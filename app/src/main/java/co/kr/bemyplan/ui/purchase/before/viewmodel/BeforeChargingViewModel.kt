package co.kr.bemyplan.ui.purchase.before.viewmodel

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.kr.bemyplan.data.local.FirebaseDefaultEventParameters
import co.kr.bemyplan.domain.repository.PreviewRepository
import co.kr.bemyplan.data.repository.scrap.PostScrapRepository
import co.kr.bemyplan.domain.model.purchase.before.PreviewContent
import co.kr.bemyplan.domain.model.purchase.before.PreviewContents
import co.kr.bemyplan.domain.model.purchase.before.PreviewInfo
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class BeforeChargingViewModel @Inject constructor(
    private val previewRepository: PreviewRepository,
    private val postScrapRepository: PostScrapRepository
) : ViewModel() {

    enum class Pay(val brand: String) {
        NAVER("네이버페이"), KAKAO("카카오페이"), TOSS("토스"), NULL("null")
    }

    private val fb = Firebase.analytics.apply {
        setDefaultEventParameters(FirebaseDefaultEventParameters.parameters)
    }

    private var _planId = -1
    val planId get() = _planId

    private var _isScraped = MutableLiveData<Boolean>()
    val isScraped: LiveData<Boolean> get() = _isScraped

    private val _payWay = MutableLiveData<Pay>(Pay.NULL)
    val payWay: LiveData<Pay> get() = _payWay

    private var _previewInfo = MutableLiveData<PreviewInfo>()
    val previewInfo: LiveData<PreviewInfo> get() = _previewInfo

    private var _previewContents = MutableLiveData<List<PreviewContents>>()
    val previewContents: LiveData<List<PreviewContents>> get() = _previewContents

    private var _previewContent = MutableLiveData<List<PreviewContent>>()
    val previewContent: LiveData<List<PreviewContent>> get() = _previewContent

    fun selectPay(way: Pay) {
        when (way) {
            Pay.KAKAO -> _payWay.value = Pay.KAKAO
            Pay.NAVER -> _payWay.value = Pay.NAVER
            Pay.TOSS -> _payWay.value = Pay.TOSS
        }
        fb.logEvent("clickPaymentMethod", Bundle().apply {
            putString("source", payWay.value.toString())
        })
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
            }.onFailure {
                Log.e("mlog: BeforeChargingViewModel::postScrap error", it.message.toString())
            }
        }
    }

    // response 로 스크랩 여부가 날아오지 않음. 이전 단계에서 받은 스크랩 여부를 적용해야 함
    fun setIsScraped(flag: Boolean) {
        _isScraped.value = flag
        Log.d("mlog: BeforeChargingViewModel::setIsScraped", isScraped.value.toString())
    }

    fun setPlanId(postId: Int) {
        _planId = postId
    }

    fun fetchPreviewPlan() {
        viewModelScope.launch {
            kotlin.runCatching {
                previewRepository.fetchPreviewPlan(planId)
            }.onSuccess { previewPlan ->
                _previewInfo.value = previewPlan.previewInfo
                _previewContents.value = previewPlan.previewContents
                // TODO: 추후 Multi ViewHolder 패턴 사용하는 게 더 깔끔할 것 같음
                val list = mutableListOf<PreviewContent>()
                for(i in 0 until previewPlan.previewContents.size step(2)) {
                    val image = previewPlan.previewContents[i].value
                    val text = previewPlan.previewContents[i+1].value
                    list.add(PreviewContent(image, text))
                }
                _previewContent.value = list
            }.onFailure { error ->
                Timber.tag("fetchPreviewPlan").e(error)
            }
        }
    }
}