package co.kr.bemyplan.ui.purchase.after.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.kr.bemyplan.domain.model.purchase.after.Contents
import co.kr.bemyplan.domain.model.purchase.after.Images
import co.kr.bemyplan.domain.model.purchase.after.Spots
import co.kr.bemyplan.domain.repository.PlanDetailRepository
import co.kr.bemyplan.ui.purchase.after.example.ExampleDummy
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AfterPurchaseViewModel @Inject constructor(
    private val planDetailRepository: PlanDetailRepository
): ViewModel() {
    // contents 들고오기
    private var _contents = MutableLiveData<List<Contents>>()
    val contents: LiveData<List<Contents>>
        get() = _contents

    // 일차별 spot
    private var _spots = MutableLiveData<List<Spots>>()
    val spots: LiveData<List<Spots>>
        get() = _spots

    // spot마다 사진들
    private var _images = MutableLiveData<List<Images>>()
    val images: LiveData<List<Images>>
        get() = _images

    // 서버 통신
    fun fetchPlanDetail(planId: Int) {
        viewModelScope.launch {
            kotlin.runCatching {
                planDetailRepository.fetchPlanDetail(planId)
            }.onSuccess { planDetail ->
                _contents.value = planDetail.contents
            }.onFailure { error ->
                Timber.tag("fetchPlanDetail").e(error)
            }
        }
    }

    // 더미데이터 생성
    fun initDummy() {
        val dummy = ExampleDummy().getPlan()
        _contents.value = dummy
    }

    // 일차별 장소들 초기화
    fun setSpots(spots: List<Spots>) {
        _spots.value = spots
    }

    // 장소별 사진들 초기화
    fun setImages(images: List<Images>) {
        _images.value = images
    }
}화