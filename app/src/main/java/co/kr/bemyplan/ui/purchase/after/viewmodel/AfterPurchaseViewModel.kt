package co.kr.bemyplan.ui.purchase.after.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.kr.bemyplan.domain.model.purchase.after.*
import co.kr.bemyplan.domain.model.purchase.after.moveInfo.Infos
import co.kr.bemyplan.domain.model.purchase.after.moveInfo.MoveInfo
import co.kr.bemyplan.domain.repository.MoveInfoRepository
import co.kr.bemyplan.domain.repository.PlanDetailRepository
import co.kr.bemyplan.ui.purchase.after.example.ExampleDummy
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AfterPurchaseViewModel @Inject constructor(
    private val planDetailRepository: PlanDetailRepository,
    private val moveInfoRepository: MoveInfoRepository
): ViewModel() {
    // plan detail 들고오고
    private var _planDetail = MutableLiveData<PlanDetail>()
    val planDetail: LiveData<PlanDetail>
        get() = _planDetail

    // contents 들고오기
    private var _contents = MutableLiveData<List<Contents>>()
    val contents: LiveData<List<Contents>>
        get() = _contents

    // 일차별 spot
    private var _spots = MutableLiveData<List<MergedPlanAndInfo>>()
    val spots: LiveData<List<MergedPlanAndInfo>>
        get() = _spots

    // spot마다 사진들
    private var _images = MutableLiveData<List<Images>>()
    val images: LiveData<List<Images>>
        get() = _images

    // 제목
    private val _author = MutableLiveData<String>()
    val author: LiveData<String>
        get() = _author

    // plan id
    private val _planId = MutableLiveData<Int>(-1)
    val planId: LiveData<Int>
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
    private val _mergedPlanAndInfo = MutableLiveData<MergedPlanAndInfo>()
    val mergedPlanAndInfo: LiveData<MergedPlanAndInfo>
        get() = _mergedPlanAndInfo

    // 서버 통신
    fun fetchPlanDetail(planId: Int) {
        viewModelScope.launch {
            kotlin.runCatching {
                planDetailRepository.fetchPlanDetail(planId)
            }.onSuccess { planDetail ->
                _planDetail.value = planDetail
            }.onFailure { error ->
                Timber.tag("fetchPlanDetail").e(error)
            }
        }
    }

    fun fetchMoveInfo(planId: Int) {
        viewModelScope.launch {
            kotlin.runCatching {
                moveInfoRepository.fetchMoveInfo(planId)
            }.onSuccess { moveInfo ->
                _moveInfoList.value = moveInfo
            }.onFailure { error ->
                Timber.tag("fetchMoveInfo").e(error)
            }
        }
    }

    // 더미데이터 생성
    fun initDummy() {
        val dummy = ExampleDummy().getPlan()
        _contents.value = dummy
    }

    // 일차별 장소들 초기화
    fun setSpots(spots: List<MergedPlanAndInfo>) {
        _spots.value = spots
    }

    // 장소별 사진들 초기화
    fun setImages(images: List<Images>) {
        _images.value = images
    }

    fun setAuthor(author: String) {
        _author.value = author
    }

    fun setPlanId(planId: Int) {
        _planId.value = planId
    }

    fun setMoveInfo(index: Int) {
        _moveInfo.value = moveInfoList.value?.get(index)
    }

    fun setMergedPlanAndInfo(day: Int, listInfos: List<Infos>, listSpots: List<Spots>) {
        val pairList = mutableListOf<Pair<Infos?, Spots>>()
        for(i in listSpots.indices) {
            if(i == listSpots.size - 1)
                pairList.add(Pair(null, listSpots[i]))
            else
                pairList.add(Pair(listInfos[i], listSpots[i]))
        }
        _mergedPlanAndInfo.value = MergedPlanAndInfo(day, pairList.toList())
    }
}