package co.kr.bemyplan.ui.purchase.after.viewmodel

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.kr.bemyplan.data.local.FirebaseDefaultEventParameters
import co.kr.bemyplan.data.repository.scrap.PostScrapRepository
import co.kr.bemyplan.domain.model.purchase.after.*
import co.kr.bemyplan.domain.model.purchase.after.moveInfo.Infos
import co.kr.bemyplan.domain.model.purchase.after.moveInfo.MoveInfo
import co.kr.bemyplan.domain.repository.MoveInfoRepository
import co.kr.bemyplan.domain.repository.PlanDetailRepository
import co.kr.bemyplan.ui.purchase.after.example.ExampleDummy
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AfterPurchaseViewModel @Inject constructor(
    private val planDetailRepository: PlanDetailRepository,
    private val moveInfoRepository: MoveInfoRepository,
    private val postScrapRepository: PostScrapRepository
): ViewModel() {

    private val fb = Firebase.analytics.apply {
        setDefaultEventParameters(FirebaseDefaultEventParameters.parameters)
    }

    // plan detail 들고오고
    private var _planDetail = MutableLiveData<PlanDetail>()
    val planDetail: LiveData<PlanDetail>
        get() = _planDetail

    // contents 들고오기
    private var _contents = MutableLiveData<List<Contents>>()
    val contents: LiveData<List<Contents>>
        get() = _contents

    // 일차별 spot
    private var _spots = MutableLiveData<List<Spots>>()
    val spots: LiveData<List<Spots>>
        get() = _spots

    // 일차별 spot
    private var _spotsWithAddress = MutableLiveData<List<SpotsWithAddress>>()
    val spotsWithAddress: LiveData<List<SpotsWithAddress>>
        get() = _spotsWithAddress

    // 스크랩
    private var _isScraped = MutableLiveData<Boolean>()
    val isScraped: LiveData<Boolean>
        get() = _isScraped

    // 작성자
    private var _authorNickname = ""
    val authorNickname
        get() = _authorNickname

    private var _authorUserId = -1
    val authorUserId
        get() = _authorUserId

    // plan id
    private var _planId = -1
    val planId
        get() = _planId

    // move info
    private val _moveInfoList = MutableLiveData<List<MoveInfo>>()
    val moveInfoList: LiveData<List<MoveInfo>>
        get() = _moveInfoList

    private val _moveInfo = MutableLiveData<MoveInfo>()
    val moveInfo: LiveData<MoveInfo>
        get() = _moveInfo

    // infos
    private val _infos = MutableLiveData<List<Infos>>()
    val infos: LiveData<List<Infos>>
        get() = _infos

    // mergedPlanAndInfo
    private val _mergedPlanAndInfoList = MutableLiveData<List<MergedPlanAndInfo>>()
    val mergedPlanAndInfoList: LiveData<List<MergedPlanAndInfo>>
        get() = _mergedPlanAndInfoList

    private val _mergedPlanAndInfo = MutableLiveData<MergedPlanAndInfo>()
    val mergedPlanAndInfo: LiveData<MergedPlanAndInfo>
        get() = _mergedPlanAndInfo

    // 서버 통신
    fun fetchPlanDetail(planId: Int) {
        viewModelScope.launch {
            kotlin.runCatching {
                planDetailRepository.fetchPlanDetail(1)
            }.onSuccess { planDetail ->
                _planDetail.value = planDetail
                _contents.value = planDetail.contents
            }.onFailure { error ->
                Timber.tag("fetchPlanDetail").e(error)
            }
        }
    }

    fun fetchMoveInfo(planId: Int) {
        viewModelScope.launch {
            kotlin.runCatching {
                moveInfoRepository.fetchMoveInfo(1)
            }.onSuccess { moveInfoList ->
                _moveInfoList.value = moveInfoList
                fetchPlanDetail(1)
            }.onFailure { error ->
                Timber.tag("fetchMoveInfo").e(error)
            }
        }
    }

    fun postScrap() {
        viewModelScope.launch {
            kotlin.runCatching {
                postScrapRepository.postScrap(planId)
            }.onSuccess {
                when (it.data.scrapped) {
                    true -> {
                        fb.logEvent("scrapTravelPlan", Bundle().apply {
                            putString("source", "BeforeChargingView")
                            putInt("postIdx", planId)
                        })
                    }
                    false -> {
                        fb.logEvent("scrapCancelTravelPlan", Bundle().apply {
                            putString("source", "BeforeChargingView")
                            putInt("postIdx", planId)
                        })
                    }
                }
                _isScraped.value = it.data.scrapped
            }.onFailure { error ->
                Timber.tag("postScrap").e(error)
            }
        }
    }

    // 더미데이터 생성
    fun initDummy() {
//        val dummy = ExampleDummy().getPlan()
//        _contents.value = dummy
    }

    // 일차별 장소들 초기화
    fun setSpots(index: Int) {
        _spots.value = contents.value?.get(index)?.spots
    }

    fun setIsScraped(flag: Boolean) {
        _isScraped.value = flag
    }

    fun setAuthor(authorNickname: String, authorUserId: Int) {
        _authorNickname = authorNickname
        _authorUserId = authorUserId
    }

    fun setPlanId(planId: Int) {
        _planId = planId
    }

    fun setMoveInfo(index: Int) {
        _moveInfo.value = moveInfoList.value?.get(index)
    }

    fun setInfos(index: Int) {
        _infos.value = moveInfoList.value?.get(index)?.infos
    }

    fun setMergedPlanAndInfoList(planDetail: PlanDetail, listMoveInfo: List<MoveInfo>) {
        val bigList = mutableListOf<MergedPlanAndInfo>()
        for (i in planDetail.contents.indices) {
            val pairList = mutableListOf<Pair<Infos?, SpotsWithAddress>>()
            val dailySpots = planDetail.contents[i].spots
            for (j in dailySpots.indices) {
                if (j == dailySpots.size - 1)
                    pairList.add(Pair(null, spotsWithAddress.value!![j]))
                else
                    pairList.add(Pair(listMoveInfo[i].infos[j], spotsWithAddress.value!![j]))
            }
            bigList.add(MergedPlanAndInfo(i + 1, pairList.toList()))
        }
        _mergedPlanAndInfoList.value = bigList
    }

    fun setMergedPlanAndInfo(index: Int) {
        _mergedPlanAndInfo.value = mergedPlanAndInfoList.value?.get(index)
    }
}