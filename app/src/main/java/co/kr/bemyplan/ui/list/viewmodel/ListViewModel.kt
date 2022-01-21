package co.kr.bemyplan.ui.list.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.kr.bemyplan.data.entity.list.ContentModel
import co.kr.bemyplan.data.repository.list.location.LocationListRepositoryImpl
import co.kr.bemyplan.data.repository.list.new.NewListRepositoryImpl
import co.kr.bemyplan.data.repository.list.suggest.SuggestListRepositoryImpl
import co.kr.bemyplan.data.repository.list.userpost.UserPostListRepositoryImpl
import co.kr.bemyplan.data.repository.main.scrap.EmptyScrapListRepositoryImpl
import co.kr.bemyplan.data.repository.main.scrap.ScrapListRepositoryImpl
import kotlinx.coroutines.launch

class ListViewModel : ViewModel() {
    private var page = 0
    private var pageSize = 10

    private var _sort = MutableLiveData<String>(CREATED_AT)
    val sort: LiveData<String> get() = _sort

    private var _newList = MutableLiveData<List<ContentModel>>()
    val newList: LiveData<List<ContentModel>> get() = _newList

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
            0 -> {
                _sort.value = "created_at"
            }
            1 -> {
                _sort.value = "order_count"
            }
            2 -> {
                _sort.value = "price"
            }
            else -> _sort.value = ""
        }
        Log.d("mlog: sort", sort.value.toString())
    }

    fun getNewList() {
        val newListRepositoryImpl = NewListRepositoryImpl()
        viewModelScope.launch {
            try {
                val response = newListRepositoryImpl.getNewList(page, pageSize)
                Log.d(
                    "mlog: ListViewModel.response.data.item.size",
                    response.data.items.size.toString()
                )
                _newList.value = response.data.items
                Log.d("mlog: ListViewModel.newList.size", newList.value?.size.toString())
            } catch (e: retrofit2.HttpException) {
                Log.e("mlog: ListViewModel::getNewList error handling", e.code().toString())
            } catch (t: Throwable) {
                Log.e("mlog: ListViewModel::getNewList error handling", t.message.toString())
            }
        }
    }

    fun getSuggestList() {
        val suggestListRepositoryImpl = SuggestListRepositoryImpl()
        viewModelScope.launch {
            try {
                val response = suggestListRepositoryImpl.getSuggestList(page, pageSize)
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
            val response = locationListRepositoryImpl.getLocationList(
                area_id,
                page,
                pageSize,
                sort.value.toString()
            )
            _locationList.value = response.data.items
            Log.d("mlog: ListViewModel.locationList.size", locationList.value?.size.toString())
        }
    }

    fun getUserPostList(userId: Int) {
        val userPostListRepositoryImpl = UserPostListRepositoryImpl()
        viewModelScope.launch {
            val response = userPostListRepositoryImpl.getUserPostList(
                userId,
                page,
                pageSize,
                sort.value.toString()
            )
            _userPostList.value = response.data.items
            Log.d("mlog: ListViewModel.userPostList.size", userPostList.value?.size.toString())
        }
    }

    fun getScrapList(user_id: String) {
        val scrapListRepositoryImpl = ScrapListRepositoryImpl()
        viewModelScope.launch {
            val response =
                scrapListRepositoryImpl.getScrapList(user_id, page, pageSize, sort.value.toString())
            _scrapList.value = response.data.items
            Log.d("mlog: ScrapViewModel.scrapList.size", scrapList.value?.size.toString())
        }
    }

    fun getEmptyScrapList() {
        val emptyScrapListRepositoryImpl = EmptyScrapListRepositoryImpl()
        viewModelScope.launch {
            val response = emptyScrapListRepositoryImpl.getEmptyScrapList()
            _emptyScrapList.value = response.data
            Log.d("mlog: EmptyScrapList.size", emptyScrapList.value?.size.toString())
        }
    }

    companion object {
        const val CREATED_AT = "created_at"
        const val BEST_SELLER = "best_seller"
        const val BEST_SCRAPPER = "best_scrapper"
    }
}