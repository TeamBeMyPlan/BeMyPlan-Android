package co.kr.bemyplan.ui.purchase.before.viewmodel

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.kr.bemyplan.data.entity.purchase.before.ContentModel
import co.kr.bemyplan.data.entity.purchase.before.PreviewInfoModel
import co.kr.bemyplan.data.local.FirebaseDefaultEventParameters
import co.kr.bemyplan.data.repository.purchase.preview.PreviewRepository
import co.kr.bemyplan.data.repository.scrap.PostScrapRepository
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
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

    private var _postId = -1
    val postId get() = _postId

    private val _payWay = MutableLiveData<Pay>(Pay.NULL)
    private var _isScraped = MutableLiveData<Boolean>()
    val isScraped: LiveData<Boolean> get() = _isScraped

    val payWay: LiveData<Pay> get() = _payWay

    private var _previewInformation = MutableLiveData<PreviewInfoModel>()
    val previewInformation: LiveData<PreviewInfoModel> get() = _previewInformation

    private var _previewList = MutableLiveData<List<ContentModel>>()
    val previewList: LiveData<List<ContentModel>> get() = _previewList

    fun selectPay(way: Pay) {
        when (way) {
            Pay.KAKAO -> _payWay.value = Pay.KAKAO
            Pay.NAVER -> _payWay.value = Pay.NAVER
            Pay.TOSS -> _payWay.value = Pay.TOSS
        }
    }

    fun postScrap() {
        viewModelScope.launch {
            kotlin.runCatching {
                postScrapRepository.postScrap(postId)
            }.onSuccess {
                when(it.data.scrapped) {
                    true -> {
                        fb.logEvent("scrapTravelPlan", Bundle().apply {
                            putString("source", "BeforeChargingView")
                            putInt("postIdx", postId)
                        })
                    }
                    false -> {
                        fb.logEvent("scrapCancelTravelPlan", Bundle().apply {
                            putString("source", "BeforeChargingView")
                            putInt("postIdx", postId)
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

    fun setPostId(postId: Int) {
        _postId = postId
    }

    fun getPreviewInfo() {
        viewModelScope.launch {
            kotlin.runCatching {
                previewRepository.getPreviewInfo(postId)
            }.onSuccess {
                _previewInformation.value = it.data
            }.onFailure {
                Log.e("mlog: BeforeChargingViewModel::getPreviewInfo error", it.message.toString())
            }
        }
    }

    fun getPreviewList() {
        viewModelScope.launch {
            kotlin.runCatching {
                previewRepository.getPreviewList(postId)
            }.onSuccess {
                _previewList.value = it.data
            }.onFailure {
                Log.e("mlog: BeforeChargingViewModel::getPreviewList", it.message.toString())
            }
        }
    }
}