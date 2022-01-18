package co.kr.bemyplan.ui.list.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.kr.bemyplan.data.entity.list.ContentModel
import co.kr.bemyplan.data.repository.list.LocationListRepositoryImpl
import co.kr.bemyplan.data.repository.list.NewListRepositoryImpl
import co.kr.bemyplan.data.repository.list.SuggestListRepositoryImpl
import co.kr.bemyplan.data.repository.list.UserPostListRepositoryImpl
import kotlinx.coroutines.launch

class ListViewModel: ViewModel() {
    private var _newList = MutableLiveData<List<ContentModel>>()
    val newList: LiveData<List<ContentModel>> get() = _newList

    private var _suggestList = MutableLiveData<List<ContentModel>>()
    val suggestList: LiveData<List<ContentModel>> get() = _suggestList

    private var _locationList = MutableLiveData<List<ContentModel>>()
    val locationList: LiveData<List<ContentModel>> get() = _locationList

    private var _userPostList = MutableLiveData<List<ContentModel>>()
    val userPostList: LiveData<List<ContentModel>> get() = _userPostList

    fun getNewList(page: Int, pageSize: Int) {
        val newListRepositoryImpl = NewListRepositoryImpl()
        viewModelScope.launch {
            val response = newListRepositoryImpl.getNewList(page, pageSize)
            Log.d("mlog: ListViewModel.response.data.item.size", response.data.items.size.toString())
            _newList.value = response.data.items
            Log.d("mlog: ListViewModel.newList.size", newList.value?.size.toString())
        }
    }

    fun getSuggestList(page: Int, pageSize: Int) {
        val suggestListRepositoryImpl = SuggestListRepositoryImpl()
        viewModelScope.launch {
            val response = suggestListRepositoryImpl.getSuggestList(page, pageSize)
            _suggestList.value = response.data.items
            Log.d("mlog: ListViewModel.suggestList.size", suggestList.value?.size.toString())
        }
    }

    fun getLocationList(area_id: Int, page: Int, pageSize: Int, sort: String) {
        val locationListRepositoryImpl = LocationListRepositoryImpl()
        viewModelScope.launch {
            val response = locationListRepositoryImpl.getLocationList(area_id, page, pageSize, sort)
            _locationList.value = response.data.items
            Log.d("mlog: ListViewModel.locationList.size", locationList.value?.size.toString())
        }
    }

    fun getUserPostList(userId: String, page: Int, pageSize: Int, sort: String) {
        val userPostListRepositoryImpl = UserPostListRepositoryImpl()
        viewModelScope.launch {
            val response = userPostListRepositoryImpl.getUserPostList(userId, page, pageSize, sort)
            _userPostList.value = response.data.items
            Log.d("mlog: ListViewModel.userPostList.size", userPostList.value?.size.toString())
        }
    }
}