package co.kr.bemyplan.ui.main.myplan.viewmodel

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.kr.bemyplan.data.local.BeMyPlanDataStore
import co.kr.bemyplan.data.local.FirebaseDefaultEventParameters
import co.kr.bemyplan.domain.model.main.myplan.MyPlanData
import co.kr.bemyplan.domain.repository.MyPlanRepository
import co.kr.bemyplan.domain.repository.ScrapRepository
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MyPlanViewModel @Inject constructor(
    private val myPlanRepository: MyPlanRepository,
    private val scrapRepository: ScrapRepository,
    dataStore: BeMyPlanDataStore
) : ViewModel() {
    private val fb = Firebase.analytics.apply {
        setDefaultEventParameters(FirebaseDefaultEventParameters.parameters)
    }

    //private var page = 0
    //private var pageSize = 10

    private var _nickname = MutableLiveData<String>()
    val nickname: LiveData<String> get() = _nickname

    //private var userId = MutableLiveData<Int>()
    private var userId : Int = 0

    private var _myPlan = MutableLiveData<List<MyPlanData.Data>>()
    val myPlan: LiveData<List<MyPlanData.Data>> get() = _myPlan

    private var lastPlanId: Int = -1

    init {
        _nickname.value = when (dataStore.nickname) {
            "" -> "로그인해주세요"
            else -> dataStore.nickname
        }
        userId = dataStore.userId
    }

    fun getMyPlanList() {
        if (userId != 0) {
            viewModelScope.launch {
                kotlin.runCatching {
                    myPlanRepository.getMyPlan(size = 4)
                }.onSuccess {
                    if (_myPlan.value != it.contents) {
                        _myPlan.value = it.contents
                        lastPlanId = it.nextCursor
                    }
                }.onFailure {
                    Timber.tag("mlog: MyPlanViewModel::getMyPlan error").e(it)
                }
            }
        }
        else{
            Log.d("network", "로그인 안 함")
        }
    }

    fun getMoreMyPlanList() {
        if (userId != 0) {
            viewModelScope.launch {
                kotlin.runCatching {
                    myPlanRepository.getMoreMyPlan(size = 4, lastPlanId)
                }.onSuccess {
                    _myPlan.value = _myPlan.value?.toMutableList()?.apply { addAll(it.contents) }
                    lastPlanId = it.nextCursor
                }.onFailure {
                    Timber.tag("mlog: MyPlanViewModel::getMyPlan error").e(it)
                }
            }
        }
    }

    fun postScrap(postId: Int) {
        viewModelScope.launch {
            kotlin.runCatching {
                scrapRepository.postScrap(postId)
            }.onSuccess {
                if (it) {
                    fb.logEvent("scrapTravelPlan", Bundle().apply {
                        putString("source", "BeforeChargingView")
                        putInt("postIdx", postId)
                    })
                }
            }.onFailure { exception ->
                Timber.e(exception)
            }
        }
    }

    fun deleteScrap(planId: Int) {
        viewModelScope.launch {
            kotlin.runCatching {
                scrapRepository.deleteScrap(planId)
            }.onSuccess {
                if (it) {
                    fb.logEvent("unScrapTravelPlan", Bundle().apply {
                        putString("source", "ListView")
                        putInt("postIdx", planId)
                    })
                }
            }.onFailure { exception ->
                Timber.e(exception)
            }
        }
    }
}