package co.kr.bemyplan.ui.list.viewmodel

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.kr.bemyplan.domain.model.list.ContentModel
import co.kr.bemyplan.data.local.FirebaseDefaultEventParameters
import co.kr.bemyplan.data.repository.list.latest.LatestListRepository
import co.kr.bemyplan.domain.repository.LocationListRepository
import co.kr.bemyplan.data.repository.list.suggest.SuggestListRepository
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

    fun getLatestList() {
        viewModelScope.launch {
            kotlin.runCatching {
                latestListRepository.getNewList(page, pageSize)
            }.onSuccess {
                if (_latestList.value != it.data.items) {
                    _latestList.value = it.data.items
                }
            }.onFailure {
                Timber.tag("mlog: ListViewModel::getLatestList error").e(it.message.toString())
            }
        }
    }

    fun getSuggestList() {
        viewModelScope.launch {
            kotlin.runCatching {
                suggestListRepository.getSuggestList(page, pageSize)
            }.onSuccess {
                if (_suggestList.value != it.data.items) {
                    _suggestList.value = it.data.items
                }
            }.onFailure {
                Timber.tag("mlog: ListViewModel::getSuggestList error").e(it.message.toString())
            }
        }
    }

    fun getLocationList(region: String, sort: String) {
        viewModelScope.launch {
            kotlin.runCatching {
                // TODO - 무한스크롤 구현 이후에는 size = 10 으로 고정할 것
                locationListRepository.getLocationList(region, size = 2, sort)
            }.onSuccess { list ->
                _locationList.value = list
            }.onFailure { error ->
                Timber.tag("mlog: ListViewModel::getLocationList error").e(error)
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