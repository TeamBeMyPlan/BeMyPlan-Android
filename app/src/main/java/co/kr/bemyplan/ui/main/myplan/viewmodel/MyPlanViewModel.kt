package co.kr.bemyplan.ui.main.myplan.viewmodel

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.kr.bemyplan.data.firebase.FirebaseAnalyticsProvider
import co.kr.bemyplan.data.local.BeMyPlanDataStore
import co.kr.bemyplan.domain.model.main.myplan.MyPlanData
import co.kr.bemyplan.domain.repository.MyPlanRepository
import co.kr.bemyplan.domain.repository.ScrapRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MyPlanViewModel @Inject constructor(
    private val myPlanRepository: MyPlanRepository,
    private val scrapRepository: ScrapRepository,
    val firebaseAnalyticsProvider: FirebaseAnalyticsProvider,
    dataStore: BeMyPlanDataStore
) : ViewModel() {
    private var _nickname = MutableLiveData<String>()
    val nickname: LiveData<String> get() = _nickname

    private var _userId = MutableLiveData<Int>()
    val userId: LiveData<Int> get() = _userId

    private var _myPlan = MutableLiveData<List<MyPlanData.Data>>()
    val myPlan: LiveData<List<MyPlanData.Data>> get() = _myPlan

    private var lastPlanId: Int = -1

    init {
        _nickname.value = when (dataStore.nickname) {
            "" -> "로그인해주세요"
            else -> dataStore.nickname
        }
        _userId.value = dataStore.userId
    }

    fun getMyPlanList() {
        Log.d("asdf", "lastPlanId : $lastPlanId")
        if (userId.value != 0) {
            viewModelScope.launch {
                kotlin.runCatching {
                    myPlanRepository.getMyPlan(size = 10)
                }.onSuccess {
                    if (_myPlan.value != it.contents) {
                        _myPlan.value = it.contents
                        lastPlanId = it.nextCursor
                    }
                }.onFailure {
                    Timber.tag("mlog: MyPlanViewModel::getMyPlan error").e(it)
                }
            }
        } else {
            Timber.tag("network").d("로그인 안 함")
        }
    }

    fun getMoreMyPlanList() {
        if (lastPlanId == -1) return
        if (userId.value != 0) {
            viewModelScope.launch {
                kotlin.runCatching {
                    Log.d("asdf", "매개변수로 넘기는 lastPlanId : $lastPlanId")
                    myPlanRepository.getMoreMyPlan(size = 10, lastPlanId)
                }.onSuccess {
                    _myPlan.value = _myPlan.value?.toMutableList()?.apply { addAll(it.contents) }
                    lastPlanId = it.nextCursor
                    Log.d("asdf", "it.nextCursor : ${it.nextCursor}")
                }.onFailure {
                    Timber.tag("mlog: MyPlanViewModel::getMyPlan error").e(it)
                }
            }
        }
    }

    fun postScrap(planId: Int) {
        viewModelScope.launch {
            kotlin.runCatching {
                scrapRepository.postScrap(planId)
            }.onSuccess {
                if (it) {
                    firebaseAnalyticsProvider.firebaseAnalytics.logEvent(
                        "scrapTravelPlan",
                        Bundle().apply {
                            putString("source", "마이플랜")
                            putInt("planId", planId)
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
                    firebaseAnalyticsProvider.firebaseAnalytics.logEvent(
                        "scrapCancelTravelPlan",
                        Bundle().apply {
                            putString("source", "마이플랜")
                            putInt("planId", planId)
                        })
                }
            }.onFailure { exception ->
                Timber.e(exception)
            }
        }
    }
}