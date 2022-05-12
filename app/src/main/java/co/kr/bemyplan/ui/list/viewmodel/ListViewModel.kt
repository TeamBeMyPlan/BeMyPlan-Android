package co.kr.bemyplan.ui.list.viewmodel

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.kr.bemyplan.domain.model.list.ContentModel
import co.kr.bemyplan.data.local.FirebaseDefaultEventParameters
import co.kr.bemyplan.domain.repository.LatestListRepository
import co.kr.bemyplan.domain.repository.LocationListRepository
import co.kr.bemyplan.domain.repository.SuggestListRepository
import co.kr.bemyplan.data.repository.list.userpost.UserPostListRepository
import co.kr.bemyplan.data.repository.scrap.PostScrapRepository
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
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
    private val postScrapRepository: PostScrapRepository
) : ViewModel() {
    private val fb = Firebase.analytics.apply {
        setDefaultEventParameters(FirebaseDefaultEventParameters.parameters)
    }

    private var page = 0
    private var pageSize = 10

    private var _latestList = MutableLiveData<List<ContentModel>>()
    val latestList: LiveData<List<ContentModel>> get() = _latestList

    private var _suggestList = MutableLiveData<List<ContentModel>>()
    val suggestList: LiveData<List<ContentModel>> get() = _suggestList

    private var _locationList = MutableLiveData<List<ContentModel>>()
    val locationList: LiveData<List<ContentModel>> get() = _locationList

    private var _userPostList = MutableLiveData<List<ContentModel>>()
    val userPostList: LiveData<List<ContentModel>> get() = _userPostList

    private var lastPlanId: Int = -1

    fun fetchLatestList() {
        viewModelScope.launch {
            kotlin.runCatching {
                latestListRepository.fetchLatestList(size = 1, "createdAt,desc")
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
                    size = 1,
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
                suggestListRepository.fetchSuggestList(size = 1)
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
                suggestListRepository.fetchMoreSuggestList(size = 1, lastPlanId)
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
                // TODO - 무한스크롤 구현 이후에는 size = 10 으로 고정할 것
                locationListRepository.fetchLocationList(region, size = 1, sort)
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
                        size = 1,
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

    fun getUserPostList(userId: Int, sort: String) {
        viewModelScope.launch {
            kotlin.runCatching {
                userPostListRepository.getUserPostList(userId, page, pageSize, sort)
            }.onSuccess {
                if (_userPostList.value != it.data.items) {
                    _userPostList.value = it.data.items
                }
            }.onFailure {
                Timber.tag("mlog: ListViewModel::getUserPostList error").e(it.message.toString())
            }
        }
    }

    fun postScrap(postId: Int) {
        viewModelScope.launch {
            kotlin.runCatching {
                postScrapRepository.postScrap(postId)
            }.onSuccess {
                when (it.data.scrapped) {
                    true -> {
                        fb.logEvent("scrapTravelPlan", Bundle().apply {
                            putString("source", "ListView")
                            putInt("postIdx", postId)
                        })
                    }
                    false -> {
                        fb.logEvent("scrapCancelTravelPlan", Bundle().apply {
                            putString("source", "ListView")
                            putInt("postIdx", postId)
                        })
                    }
                }
                Timber.tag("mlog: postScrap").d("success")
            }.onFailure {
                Timber.tag("mlog: postScrap").d("fail")
            }
        }
    }

    companion object {
        const val size = 10
    }
}