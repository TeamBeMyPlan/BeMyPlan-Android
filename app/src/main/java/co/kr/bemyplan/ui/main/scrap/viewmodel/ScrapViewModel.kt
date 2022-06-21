package co.kr.bemyplan.ui.main.scrap.viewmodel

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.kr.bemyplan.data.firebase.FirebaseAnalyticsProvider
import co.kr.bemyplan.domain.model.list.ContentModel
import co.kr.bemyplan.domain.repository.CheckPurchasedRepository
import co.kr.bemyplan.domain.repository.ScrapListRepository
import co.kr.bemyplan.domain.repository.ScrapRepository
import co.kr.bemyplan.util.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ScrapViewModel @Inject constructor(
    private val scrapListRepository: ScrapListRepository,
    private val scrapRepository: ScrapRepository,
    private val checkPurchasedRepository: CheckPurchasedRepository
) : ViewModel() {
    @Inject
    lateinit var firebaseAnalyticsProvider: FirebaseAnalyticsProvider

    private var _scrapList = MutableLiveData<List<ContentModel>>()
    val scrapList: LiveData<List<ContentModel>> get() = _scrapList
    private var _emptyScrapList = MutableLiveData<List<ContentModel>>()
    val emptyScrapList: LiveData<List<ContentModel>> get() = _emptyScrapList
    private var lastPlanId: Int = -1

    val isPurchased = SingleLiveEvent<Unit>()
    val isNotPurchased = SingleLiveEvent<Unit>()

    fun getScrapList(sort: String) {
        viewModelScope.launch {
            kotlin.runCatching {
                if (sort == "id,desc") {
                    scrapListRepository.fetchDefaultScrapList()
                } else {
                    scrapListRepository.fetchQueryScrapList(sort)
                }
            }.onSuccess { response ->
                _scrapList.value = response.contents
                lastPlanId = response.nextCursor
            }.onFailure {
                Timber.tag("mlog: ListViewModel::getScrapList error").e(it.message.toString())
            }
        }
    }

    fun getEmptyScrapList() {
        viewModelScope.launch {
            kotlin.runCatching {
                scrapListRepository.fetchEmptyScrapList()
            }.onSuccess {
                if (_emptyScrapList.value != it.data) {
                    _emptyScrapList.value = it.data
                }
            }.onFailure {
                Timber.tag("mlog: ListViewModel::getEmptyScrapList error").e(it.message.toString())
            }
        }
    }

    fun getMoreScrapList(sort: String) {
        viewModelScope.launch {
            runCatching {
                if (sort == "id,desc") {
                    scrapListRepository.fetchDefaultMoreScrapList(size, lastPlanId)
                } else {
                    scrapListRepository.fetchQueryMoreScrapList(size, lastPlanId, sort)
                }
            }.onSuccess {
                _scrapList.value = scrapList.value?.toMutableList()?.apply { addAll(it.contents) }
                lastPlanId = it.nextCursor
            }.onFailure {
                Timber.e(it)
            }
        }
    }

    fun postScrap(planId: Int) {
        viewModelScope.launch {
            kotlin.runCatching {
                scrapRepository.postScrap(planId)
            }.onSuccess {
                if (it) {
                    firebaseAnalyticsProvider.firebaseAnalytics.logEvent(
                        "scrapTravelPlan",
                        Bundle().apply {
                            putString("source", "스크랩")
                            putInt("planId", planId)
                        })
                }
            }.onFailure { exception ->
                Timber.e(exception)
            }
        }
    }

    fun deleteScrap(planId: Int) {
        viewModelScope.launch {
            kotlin.runCatching {
                scrapRepository.deleteScrap(planId)
            }.onSuccess {
                if (it) {
                    firebaseAnalyticsProvider.firebaseAnalytics.logEvent(
                        "scrapCancelTravelPlan",
                        Bundle().apply {
                            putString("source", "스크랩")
                            putInt("planId", planId)
                        })
                }
            }.onFailure { exception ->
                Timber.e(exception)
            }
        }
    }

    fun checkPurchased(planId: Int) {
        viewModelScope.launch {
            runCatching {
                checkPurchasedRepository.checkPurchased(planId)
            }.onSuccess {
                isNotPurchased.call()
            }.onFailure {
                isPurchased.call()
            }
        }
    }

    companion object {
        const val size = 10
    }
}