package co.kr.bemyplan.ui.main.scrap.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.kr.bemyplan.data.entity.main.scrap.ContentModel
import co.kr.bemyplan.data.repository.main.scrap.ScrapListRepositoryImpl
import kotlinx.coroutines.launch

class ScrapViewModel : ViewModel() {
    private val scrapListRepositoryImpl = ScrapListRepositoryImpl()

    private var _scrapList = MutableLiveData<List<ContentModel>>()
    val scrapList: LiveData<List<ContentModel>> get() = _scrapList

    fun getScrapList(user_id: String, page: Int, pageSize: Int, sort: String) {
        viewModelScope.launch {
            val response = scrapListRepositoryImpl.getScrapList(user_id, page, pageSize, sort)
            _scrapList.value = response.data
            Log.d("mlog: ScrapViewModel.scrapList.size", scrapList.value?.size.toString())
        }
    }
}