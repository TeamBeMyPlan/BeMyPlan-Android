package co.kr.bemyplan.ui.main.scrap.viewmodel

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.kr.bemyplan.domain.model.list.ContentModel
import co.kr.bemyplan.data.local.FirebaseDefaultEventParameters
import co.kr.bemyplan.data.repository.main.scrap.ScrapRepository
import co.kr.bemyplan.data.repository.scrap.PostScrapRepository
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ScrapViewModel @Inject constructor(
    private val scrapListRepository: ScrapRepository,
    private val postScrapRepository: PostScrapRepository
): ViewModel() {
    private val fb = Firebase.analytics.apply {
        setDefaultEventParameters(FirebaseDefaultEventParameters.parameters)
    }

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
                Timber.tag("mlog: ListViewModel::getScrapList error").e(it.message.toString())
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
                Timber.tag("mlog: ListViewModel::getEmptyScrapList error").e(it.message.toString())
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
                            putString("source", "ScrapView")
                            putInt("postIdx", postId)
                        })
                    }
                    false -> {
                        fb.logEvent("scrapCancelTravelPlan", Bundle().apply {
                            putString("source", "ScrapView")
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
}