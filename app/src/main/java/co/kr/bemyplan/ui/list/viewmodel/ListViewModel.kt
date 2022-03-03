package co.kr.bemyplan.ui.list.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.kr.bemyplan.data.entity.list.ContentModel
import co.kr.bemyplan.data.repository.list.latest.LatestListRepository
import co.kr.bemyplan.data.repository.list.location.LocationListRepository
import co.kr.bemyplan.data.repository.list.suggest.SuggestListRepository
import co.kr.bemyplan.data.repository.list.userpost.UserPostListRepository
import co.kr.bemyplan.data.repository.main.scrap.ScrapRepository
import co.kr.bemyplan.data.repository.scrap.PostScrapRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val latestListRepository: LatestListRepository,
    private val suggestListRepository: SuggestListRepository,
    private val locationListRepository: LocationListRepository,
    private val userPostListRepository: UserPostListRepository,
    private val postScrapRepository: PostScrapRepository
) : ViewModel() {
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
                if(_latestList.value != it.data.items) {
                    _latestList.value = it.data.items
                }
            }.onFailure {
                Log.e("mlog: ListViewModel::getLatestList error", it.message.toString())
            }
        }
    }

    fun getSuggestList() {
        viewModelScope.launch {
            kotlin.runCatching {
                suggestListRepository.getSuggestList(page, pageSize)
            }.onSuccess {
                if(_suggestList.value != it.data.items) {
                    _suggestList.value = it.data.items
                }
            }.onFailure {
                Log.e("mlog: ListViewModel::getSuggestList error", it.message.toString())
            }
        }
    }

    fun getLocationList(areaId: Int, sort: String) {
        viewModelScope.launch {
            kotlin.runCatching {
                locationListRepository.getLocationList(areaId, page, pageSize, sort)
            }.onSuccess {
                if(_locationList.value != it.data.items) {
                    _locationList.value = it.data.items
                }
            }.onFailure {
                Log.e("mlog: ListViewModel::getLocationList error", it.message.toString())
            }
        }
    }

    fun getUserPostList(userId: Int, sort: String) {
        viewModelScope.launch {
            kotlin.runCatching {
                userPostListRepository.getUserPostList(userId, page, pageSize, sort)
            }.onSuccess {
                if(_userPostList.value != it.data.items) {
                    _userPostList.value = it.data.items
                }
            }.onFailure{
                Log.e("mlog: ListViewModel::getUserPostList error", it.message.toString())
            }
        }
    }

    fun postScrap(postId: Int) {
        viewModelScope.launch {
            kotlin.runCatching {
                postScrapRepository.postScrap(postId)
            }.onSuccess {
                Log.d("mlog: postScrap", "success")
            }.onFailure {
                Log.d("mlog: postScrap", "fail")
            }
        }
    }
}