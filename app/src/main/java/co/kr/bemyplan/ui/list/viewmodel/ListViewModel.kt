package co.kr.bemyplan.ui.list.viewmodel

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.kr.bemyplan.data.firebase.FirebaseAnalyticsProvider
import co.kr.bemyplan.domain.model.list.ContentModel
import co.kr.bemyplan.domain.repository.*
import co.kr.bemyplan.util.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val latestListRepository: LatestListRepository,
    private val suggestListRepository: SuggestListRepository,
    private val locationListRepository: LocationListRepository,
    private val userPostListRepository: UserPostListRepository,
    private val scrapRepository: ScrapRepository,
    private val checkPurchasedRepository: CheckPurchasedRepository,
    val firebaseAnalyticsProvider: FirebaseAnalyticsProvider
) : ViewModel() {
    private var _authorUserId = -1
    val authorUserId get() = _authorUserId

    private var _latestList = MutableLiveData<List<ContentModel>>()
    val latestList: LiveData<List<ContentModel>> get() = _latestList

    private var _suggestList = MutableLiveData<List<ContentModel>>()
    val suggestList: LiveData<List<ContentModel>> get() = _suggestList

    private var _locationList = MutableLiveData<List<ContentModel>>()
    val locationList: LiveData<List<ContentModel>> get() = _locationList

    private var _userPostList = MutableLiveData<List<ContentModel>>()
    val userPostList: LiveData<List<ContentModel>> get() = _userPostList

    private var lastPlanId: Int = -1
    val isPurchased = SingleLiveEvent<Unit>()
    val isNotPurchased = SingleLiveEvent<Unit>()
    var from = ""

    fun setAuthorUserId(userId: Int) {
        _authorUserId = userId
    }

    fun fetchLatestList() {
        viewModelScope.launch {
            kotlin.runCatching {
                latestListRepository.fetchLatestList(size, "createdAt,desc")
            }.onSuccess { response ->
                _latestList.value = response.contents
                lastPlanId = response.nextCursor
            }.onFailure {
                Timber.tag("mlog: ListViewModel::fetchLatestList error").e(it)
            }
        }
    }

    fun fetchMoreLatestList() {
        viewModelScope.launch {
            kotlin.runCatching {
                latestListRepository.fetchMoreLatestList(
                    size,
                    "createdAt,desc",
                    lastPlanId
                )
            }.onSuccess { response ->
                _latestList.value =
                    _latestList.value?.toMutableList()?.apply { addAll(response.contents) }
                lastPlanId = response.nextCursor
            }.onFailure {
                Timber.tag("mlog: ListViewModel::fetchMoreList error").e(it)
            }
        }
    }

    fun fetchSuggestList() {
        viewModelScope.launch {
            kotlin.runCatching {
                suggestListRepository.fetchSuggestList(size)
            }.onSuccess { response ->
                _suggestList.value = response.contents
                lastPlanId = response.nextCursor
            }.onFailure {
                Timber.tag("mlog: ListViewModel::fetchSuggestList error").e(it)
            }
        }
    }

    fun fetchMoreSuggestList() {
        viewModelScope.launch {
            kotlin.runCatching {
                suggestListRepository.fetchMoreSuggestList(size, lastPlanId)
            }.onSuccess { response ->
                _suggestList.value =
                    _suggestList.value?.toMutableList()?.apply { addAll(response.contents) }
                lastPlanId = response.nextCursor
            }.onFailure {
                Timber.tag("mlog: ListViewModel::fetchMoreSuggestList error").e(it)
            }
        }
    }

    fun fetchLocationList(region: String, sort: String) {
        viewModelScope.launch {
            kotlin.runCatching {
                locationListRepository.fetchLocationList(region, size, sort)
            }.onSuccess { response ->
                _locationList.value = response.contents
                lastPlanId = response.nextCursor
            }.onFailure { error ->
                Timber.tag("mlog: ListViewModel::getLocationList error").e(error)
            }
        }
    }

    fun fetchMoreLocationList(region: String, sort: String) {
        viewModelScope.launch {
            if (lastPlanId != -1) {
                kotlin.runCatching {
                    locationListRepository.fetchMoreLocationList(
                        region,
                        size,
                        sort,
                        lastPlanId
                    )
                }.onSuccess { response ->
                    _locationList.value =
                        _locationList.value?.toMutableList()
                            ?.apply { addAll(response.contents) }
                    lastPlanId = response.nextCursor
                }
            }
        }
    }

    fun fetchUserPlanList(sort: String) {
        viewModelScope.launch {
            kotlin.runCatching {
                userPostListRepository.fetchUserPlanList(size, sort, authorUserId)
            }.onSuccess { response ->
                _userPostList.value = response.contents
                lastPlanId = response.nextCursor
            }.onFailure {
                Timber.tag("mlog: ListViewModel::getUserPostList error").e(it.message.toString())
            }
        }
    }

    fun fetchMoreUserPlanList(sort: String) {
        viewModelScope.launch {
            kotlin.runCatching {
                userPostListRepository.fetchMoreUserPlanList(
                    size,
                    sort,
                    authorUserId,
                    lastPlanId
                )
            }.onSuccess { response ->
                _userPostList.value =
                    _userPostList.value?.toMutableList()?.apply { addAll(response.contents) }
                lastPlanId = response.nextCursor
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
                            putString(
                                "source", when (from) {
                                    "new", "suggest" -> "홈"
                                    "location" -> "여행지"
                                    "user" -> "작성자 이름"
                                    else -> throw IllegalArgumentException()
                                }
                            )
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
                            putString(
                                "source", when (from) {
                                    "new", "suggest" -> "홈"
                                    "location" -> "여행지"
                                    "user" -> "작성자 이름"
                                    else -> throw IllegalArgumentException()
                                }
                            )
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