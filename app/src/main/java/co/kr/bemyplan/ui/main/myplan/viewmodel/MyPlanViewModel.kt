package co.kr.bemyplan.ui.main.myplan.viewmodel

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.kr.bemyplan.data.local.FirebaseDefaultEventParameters
import co.kr.bemyplan.data.repository.scrap.PostScrapRepository
import co.kr.bemyplan.domain.model.list.ContentModel
import co.kr.bemyplan.domain.repository.MyPlanRepository
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MyPlanViewModel @Inject constructor(
    private val myPlanRepository: MyPlanRepository,
    private val postScrapRepository: PostScrapRepository
) : ViewModel() {
    private val fb = Firebase.analytics.apply {
        setDefaultEventParameters(FirebaseDefaultEventParameters.parameters)
    }

    private var page = 0
    private var pageSize = 10
    private var lastPlanId: Int = -1

    private var _nickname = MutableLiveData<String>()
    val nickname: LiveData<String> get() = _nickname

    private var _myPlanList = MutableLiveData<List<ContentModel>>()
    val myPlanList: LiveData<List<ContentModel>> get() = _myPlanList

    fun setNickname(nickname: String) {
        _nickname.value = nickname
    }

    fun fetchMyPlanList() {
        viewModelScope.launch {
            kotlin.runCatching {
                myPlanRepository.fetchMyPlanList(size = 1, sort = "id,desc")
            }.onSuccess { response ->
                _myPlanList.value = response.contents
                lastPlanId = response.nextCursor
            }.onFailure { error ->
                Timber.tag("mlog: MyPlanViewModel::fetchMyPlanList error").e(error)
            }
        }
    }

    fun fetchMoreMyPlanList() {
        viewModelScope.launch {
            if (lastPlanId != -1) {
                kotlin.runCatching {
                    myPlanRepository.fetchMoreMyPlanList(size = 1, sort = "id,desc", lastPlanId)
                }.onSuccess { response ->
                    _myPlanList.value = _myPlanList.value?.toMutableList()
                        ?.apply { addAll(response.contents) }
                    lastPlanId = response.nextCursor
                }
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
}