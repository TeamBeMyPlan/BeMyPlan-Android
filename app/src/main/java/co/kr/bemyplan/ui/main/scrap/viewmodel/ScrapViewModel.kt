package co.kr.bemyplan.ui.main.scrap.viewmodel

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.kr.bemyplan.data.local.FirebaseDefaultEventParameters
import co.kr.bemyplan.domain.repository.ScrapListRepository
import co.kr.bemyplan.domain.model.list.ContentModel
import co.kr.bemyplan.domain.repository.ScrapRepository
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ScrapViewModel @Inject constructor(
    private val scrapListRepository: ScrapListRepository,
    private val scrapRepository: ScrapRepository
) : ViewModel() {
    private val fb = Firebase.analytics.apply {
        setDefaultEventParameters(FirebaseDefaultEventParameters.parameters)
    }

    private var page = 0
    private var pageSize = 10

    private var _scrapList = MutableLiveData<List<ContentModel>>()
    val scrapList: LiveData<List<ContentModel>> get() = _scrapList

    private var _emptyScrapList = MutableLiveData<List<ContentModel>>()
    val emptyScrapList: LiveData<List<ContentModel>> get() = _emptyScrapList

    private var lastPlanId: Int = -1

    fun getScrapList(sort: String) {
        viewModelScope.launch {
            kotlin.runCatching {
                scrapListRepository.getScrapList(sort)
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
                scrapListRepository.getEmptyScrapList()
            }.onSuccess {
                if (_emptyScrapList.value != it.data) {
                    _emptyScrapList.value = it.data
                }
            }.onFailure {
                Timber.tag("mlog: ListViewModel::getEmptyScrapList error").e(it.message.toString())
            }
        }
    }

    fun postScrap(postId: Int) {
        viewModelScope.launch {
            kotlin.runCatching {
                scrapRepository.postScrap(postId)
            }.onSuccess {
                if(it) {
                    fb.logEvent("scrapTravelPlan", Bundle().apply {
                        putString("source", "ScrapView")
                        putInt("postIdx", postId)
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
                if(it) {
                    fb.logEvent("unScrapTravelPlan", Bundle().apply {
                        putString("source", "ListView")
                        putInt("postIdx", planId)
                    })
                }
            }.onFailure { exception ->
                Timber.e(exception)
            }
        }
    }
}