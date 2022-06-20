package co.kr.bemyplan.ui.purchase.before.viewmodel

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.kr.bemyplan.data.firebase.FirebaseAnalyticsProvider
import co.kr.bemyplan.domain.model.purchase.before.PreviewContent
import co.kr.bemyplan.domain.model.purchase.before.PreviewContents
import co.kr.bemyplan.domain.model.purchase.before.PreviewInfo
import co.kr.bemyplan.domain.repository.PreviewRepository
import co.kr.bemyplan.domain.repository.PurchaseRepository
import co.kr.bemyplan.domain.repository.ScrapRepository
import co.kr.bemyplan.util.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class BeforeChargingViewModel @Inject constructor(
    private val previewRepository: PreviewRepository,
    private val scrapRepository: ScrapRepository,
    private val purchaseRepository: PurchaseRepository
) : ViewModel() {

    enum class Pay(val brand: String) {
        NAVER("네이버페이"), KAKAO("카카오페이"), TOSS("토스"), NULL("null")
    }

    @Inject
    lateinit var firebaseAnalyticsProvider: FirebaseAnalyticsProvider

    private var _planId = -1
    val planId get() = _planId

    private var _scrapStatus = MutableLiveData<Boolean>()
    val scrapStatus: LiveData<Boolean> get() = _scrapStatus

    private var _authorNickname = ""
    val authorNickname get() = _authorNickname

    private var _authorUserId = -1
    val authorUserId get() = _authorUserId

    private var _thumbnail = ""
    val thumbnail get() = _thumbnail

    private var _payWay = MutableLiveData<Pay>(Pay.NULL)
    val payWay: LiveData<Pay> get() = _payWay

    private var _purchaseSuccess = SingleLiveEvent<Boolean>()
    val purchaseSuccess: LiveData<Boolean> get() = _purchaseSuccess

    private var _previewInfo = MutableLiveData<PreviewInfo>()
    val previewInfo: LiveData<PreviewInfo> get() = _previewInfo

    private var _previewContents = MutableLiveData<List<PreviewContents>>()
    val previewContents: LiveData<List<PreviewContents>> get() = _previewContents

    private var _previewContent = MutableLiveData<List<PreviewContent>>()
    val previewContent: LiveData<List<PreviewContent>> get() = _previewContent

    // response 로 스크랩 여부가 날아오지 않음. 이전 단계에서 받은 스크랩 여부를 적용해야 함
    fun setScrapStatus(flag: Boolean) {
        _scrapStatus.value = flag
        Timber.tag("mlog: BeforeChargingViewModel::setIsScraped").d(scrapStatus.value.toString())
    }

    fun setPlanId(postId: Int) {
        _planId = postId
    }

    fun setAuthor(nickname: String, userId: Int) {
        _authorNickname = nickname
        _authorUserId = userId
    }

    fun setThumbnail(thumbnail: String) {
        _thumbnail = thumbnail
    }

    fun selectPay(way: Pay) {
        when (way) {
            Pay.KAKAO -> _payWay.value = Pay.KAKAO
            Pay.NAVER -> _payWay.value = Pay.NAVER
            Pay.TOSS -> _payWay.value = Pay.TOSS
            else -> throw IndexOutOfBoundsException()
        }
        fb.logEvent("clickPaymentMethod", Bundle().apply {
            putString("source", payWay.value.toString())
        })
    }

    fun scrap() {
        when (requireNotNull(scrapStatus.value)) {
            true -> deleteScrap()
            false -> postScrap()
        }
    }

    private fun postScrap() {
        viewModelScope.launch {
            kotlin.runCatching {
                scrapRepository.postScrap(planId)
            }.onSuccess {
                if (it) {
                    firebaseAnalyticsProvider.firebaseAnalytics.logEvent(
                        "scrapTravelPlan",
                        Bundle().apply {
                            putString("source", "구매 전 여행일정 미리보기")
                            putInt("planId", planId)
                        }
                    )
                    _scrapStatus.value = true
                }
            }.onFailure { exception ->
                Timber.e(exception)
            }
        }
    }

    private fun deleteScrap() {
        viewModelScope.launch {
            kotlin.runCatching {
                scrapRepository.deleteScrap(planId)
            }.onSuccess {
                if (it) {
                    firebaseAnalyticsProvider.firebaseAnalytics.logEvent(
                        "scrapCancelTravelPlan",
                        Bundle().apply {
                            putString("source", "구매 전 여행일정 미리보기")
                            putInt("planId", planId)
                        }
                    )
                    _scrapStatus.value = false
                }
            }.onFailure { exception ->
                Timber.e(exception)
            }
        }
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
                for (i in 0 until previewPlan.previewContents.size step (2)) {
                    val image = previewPlan.previewContents[i].value
                    val text = previewPlan.previewContents[i + 1].value
                    list.add(PreviewContent(image, text))
                }
                _previewContent.value = list
            }.onFailure { error ->
                Timber.tag("fetchPreviewPlan").e(error)
            }
        }
    }

    fun purchasePlan() {
        if (planId != -1) {
            viewModelScope.launch {
                purchaseRepository.purchase(planId)
                    .onSuccess {
                        _purchaseSuccess.value = true
                        Timber.i("$planId 구매 성공")
                    }
                    .onFailure { exception ->
                        _purchaseSuccess.value = false
                        Timber.e("$planId 구매 실패: $exception")
                    }
            }
        }
    }

    fun checkScrapStatus() {
        if(planId != -1) {
            viewModelScope.launch {
                runCatching {
                    scrapRepository.checkScrapStatus(planId)
                }.onSuccess {
                    if(it) {
                        _scrapStatus.value = true
                    }
                }.onFailure {
                    _scrapStatus.value = false
                    Timber.e(it)
                }
            }
        }
    }
}