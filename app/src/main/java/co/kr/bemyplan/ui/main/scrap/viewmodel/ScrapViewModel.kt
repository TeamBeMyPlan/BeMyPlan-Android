package co.kr.bemyplan.ui.main.scrap.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.kr.bemyplan.data.entity.list.ContentModel
import co.kr.bemyplan.data.repository.main.scrap.ScrapRepository
import co.kr.bemyplan.data.repository.scrap.PostScrapRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScrapViewModel @Inject constructor(
    private val scrapListRepository: ScrapRepository,
    private val postScrapRepository: PostScrapRepository
): ViewModel() {
    private var page = 0
    private var pageSize = 10

    private var _scrapList = MutableLiveData<List<ContentModel>>()
    val scrapList: LiveData<List<ContentModel>> get() = _scrapList

    private var _emptyScrapList = MutableLiveData<List<ContentModel>>()
    val emptyScrapList: LiveData<List<ContentModel>> get() = _emptyScrapList

    fun getScrapList(sort: String) {
        viewModelScope.launch {
            kotlin.runCatching {
                scrapListRepository.getScrapList(page, pageSize, sort)
            }.onSuccess {
                if(_scrapList.value != it.data.items) {
                    _scrapList.value = it.data.items
                }
            }.onFailure {
                Log.e("mlog: ListViewModel::getScrapList error", it.message.toString())
            }
        }
    }

    fun getEmptyScrapList() {
        viewModelScope.launch {
            kotlin.runCatching {
                scrapListRepository.getEmptyScrapList()
            }.onSuccess {
                if(_emptyScrapList.value != it.data) {
                    _emptyScrapList.value = it.data
                }
            }.onFailure {
                Log.e("mlog: ListViewModel::getEmptyScrapList error", it.message.toString())
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