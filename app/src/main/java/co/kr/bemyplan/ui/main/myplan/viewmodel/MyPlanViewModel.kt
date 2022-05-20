package co.kr.bemyplan.ui.main.myplan.viewmodel

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.kr.bemyplan.data.entity.main.myplan.MyModel
import co.kr.bemyplan.data.local.FirebaseDefaultEventParameters
import co.kr.bemyplan.data.repository.main.myplan.MyPlanRepository
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
    private val scrapRepository: ScrapRepository
) : ViewModel() {
    private val fb = Firebase.analytics.apply {
        setDefaultEventParameters(FirebaseDefaultEventParameters.parameters)
    }

    private var page = 0
    private var pageSize = 10

    private var _nickname = MutableLiveData<String>()
    val nickname: LiveData<String> get() = _nickname

    private var _myPlan = MutableLiveData<List<MyModel>>()
    val myPlan: LiveData<List<MyModel>> get() = _myPlan

    fun setNickname(nickname: String) {
        _nickname.value = nickname
    }

    fun getMyPlanList() {
        viewModelScope.launch {
            kotlin.runCatching {
                myPlanRepository.getMyPlan(page, pageSize)
            }.onSuccess {
                if(_myPlan.value != it.data.items) {
                    _myPlan.value = it.data.items
                }
            }.onFailure {
                Timber.tag("mlog: MyPlanViewModel::getMyPlan error").e(it)
            }
        }
    }

    fun postScrap(postId: Int) {
        viewModelScope.launch {
            kotlin.runCatching {
                scrapRepository.postScrap(postId)
            }.onSuccess {
                if(it) {
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
                if(it) {
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