package co.kr.bemyplan.ui.list.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.kr.bemyplan.data.entity.list.ContentModel
import co.kr.bemyplan.data.repository.list.location.LocationListRepositoryImpl
import co.kr.bemyplan.data.repository.list.latest.LatestListRepository
import co.kr.bemyplan.data.repository.list.suggest.SuggestListRepository
import co.kr.bemyplan.data.repository.list.suggest.SuggestListRepositoryImpl
import co.kr.bemyplan.data.repository.list.userpost.UserPostListRepositoryImpl
import co.kr.bemyplan.data.repository.main.scrap.PostScrapRepositoryImpl
import co.kr.bemyplan.data.repository.main.scrap.ScrapRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val latestListRepository: LatestListRepository,
    private val suggestListRepository: SuggestListRepository,
    private val scrapListRepository: ScrapRepository
) : ViewModel() {
    private var page = 0
    private var pageSize = 10

    private var _sort = MutableLiveData<String>("created_at")
    val sort: LiveData<String> get() = _sort

    private var _latestList = MutableLiveData<List<ContentModel>>()
    val latestList: LiveData<List<ContentModel>> get() = _latestList

    private var _suggestList = MutableLiveData<List<ContentModel>>()
    val suggestList: LiveData<List<ContentModel>> get() = _suggestList

    private var _locationList = MutableLiveData<List<ContentModel>>()
    val locationList: LiveData<List<ContentModel>> get() = _locationList

    private var _userPostList = MutableLiveData<List<ContentModel>>()
    val userPostList: LiveData<List<ContentModel>> get() = _userPostList

    private var _scrapList = MutableLiveData<List<ContentModel>>()
    val scrapList: LiveData<List<ContentModel>> get() = _scrapList

    private var _emptyScrapList = MutableLiveData<List<ContentModel>>()
    val emptyScrapList: LiveData<List<ContentModel>> get() = _emptyScrapList

    fun setSort(sortType: Int) {
        when (sortType) {
            0 -> _sort.value = "created_at"
            1 -> _sort.value = "order_count"
            2 -> _sort.value = "price"
            else -> _sort.value = ""
        }
        Log.d("mlog: sort", sort.value.toString())
    }

    fun getLatestList() {
        viewModelScope.launch {
            try {
                val response = latestListRepository.getNewList(page, pageSize)
                _latestList.value = response.data.items
                Log.d("mlog: ListViewModel.latestList.size", latestList.value?.size.toString())
            } catch (e: retrofit2.HttpException) {
                Log.e("mlog: ListViewModel::getLatestList error handling", e.code().toString())
            } catch (t: Throwable) {
                Log.e("mlog: ListViewModel::getLatestList error handling", t.message.toString())
            }
        }
    }

    fun getSuggestList() {
        viewModelScope.launch {
            try {
                val response = suggestListRepository.getSuggestList(page, pageSize)
                _suggestList.value = response.data.items
                Log.d("mlog: ListViewModel.suggestList.size", suggestList.value?.size.toString())
            } catch (e: retrofit2.HttpException) {
                Log.e("mlog: ListViewModel::getSuggestList error handling", e.code().toString())
            } catch (t: Throwable) {
                Log.e("mlog: ListViewModel::getSuggestList error handling", t.message.toString())
            }
        }
    }

    fun getLocationList(area_id: Int) {
        val locationListRepositoryImpl = LocationListRepositoryImpl()
        viewModelScope.launch {
            try {
                val response = locationListRepositoryImpl.getLocationList(
                    area_id,
                    page,
                    pageSize,
                    sort.value.toString()
                )
                _locationList.value = response.data.items
                Log.d("mlog: ListViewModel.locationList.size", locationList.value?.size.toString())
            } catch (e: retrofit2.HttpException) {
                Log.e("mlog: ListViewModel::getLocation error handling", e.code().toString())
            } catch (t: Throwable) {
                Log.e("mlog: ListViewModel::getLocationList error handling", t.message.toString())
            }
        }
    }

    fun getUserPostList(userId: Int) {
        val userPostListRepositoryImpl = UserPostListRepositoryImpl()
        viewModelScope.launch {
            try {
                val response = userPostListRepositoryImpl.getUserPostList(
                    userId,
                    page,
                    pageSize,
                    sort.value.toString()
                )
                _userPostList.value = response.data.items
                Log.d("mlog: ListViewModel.userPostList.size", userPostList.value?.size.toString())
            } catch (e: retrofit2.HttpException) {
                Log.e("mlog: ListViewModel::getUserPostList error handling", e.code().toString())
            } catch (t: Throwable) {
                Log.e("mlog: ListViewModel::getUserPostList error handling", t.message.toString())
            }
        }
    }

    fun getScrapList() {
        viewModelScope.launch {
            try {
                val response =
                    scrapListRepository.getScrapList(
                        page,
                        pageSize,
                        sort.value.toString()
                    )
                _scrapList.value = response.data.items
                Log.d("mlog: ScrapViewModel.scrapList.size", scrapList.value?.size.toString())
            } catch (e: retrofit2.HttpException) {
                Log.e("mlog: ListViewModel::getScrapList error handling", e.code().toString())
            } catch (t: Throwable) {
                Log.e("mlog: ListViewModel::getScrapList error handling", t.message.toString())
            }
        }
    }

    fun getEmptyScrapList() {
        viewModelScope.launch {
            try {
                val response = scrapListRepository.getEmptyScrapList()
                _emptyScrapList.value = response.data
                Log.d("mlog: EmptyScrapList.size", emptyScrapList.value?.size.toString())
            } catch (e: retrofit2.HttpException) {
                Log.e("mlog: ListViewModel::getEmptyScrapList error handling", e.code().toString())
            } catch (t: Throwable) {
                Log.e("mlog: ListViewModel::getEmptyScrapList error handling", t.message.toString())
            }
        }
    }

    fun postScrap(postId: Int) {
        val postScrapRepositoryImpl = PostScrapRepositoryImpl()
        viewModelScope.launch {
            kotlin.runCatching {
                postScrapRepositoryImpl.postScrap(postId)
            }.onSuccess {
                Log.d("mlog: postScrap", "success")
            }.onFailure {
                Log.d("mlog: postScrap", "fail")
            }
        }
    }
}