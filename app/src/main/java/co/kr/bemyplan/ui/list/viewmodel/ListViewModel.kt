package co.kr.bemyplan.ui.list.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.kr.bemyplan.data.entity.list.ContentModel
import co.kr.bemyplan.data.repository.list.NewListRepositoryImpl
import kotlinx.coroutines.launch

class ListViewModel: ViewModel() {
    private val newListRepositoryImpl = NewListRepositoryImpl()

    private var _newList = MutableLiveData<List<ContentModel>>()
    val newList: LiveData<List<ContentModel>> get() = _newList

    fun getNewList(page: Int) {
        viewModelScope.launch {
            val response = newListRepositoryImpl.getNewList(page)
            _newList.value = response.data
            Log.d("mlog: ListViewModel.newList.size", newList.value?.size.toString())
        }
    }
}