package co.kr.bemyplan.ui.purchase.before.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.kr.bemyplan.data.entity.purchase.before.ContentModel
import co.kr.bemyplan.data.entity.purchase.before.PreviewInfoModel
import co.kr.bemyplan.data.repository.list.preview.PreviewInfoRepositoryImpl
import co.kr.bemyplan.data.repository.list.preview.PreviewListRepositoryImpl
import kotlinx.coroutines.launch


class BeforeChargingViewModel : ViewModel() {

    enum class Pay(val brand: String) {
        NAVER("네이버페이"), KAKAO("카카오페이"), TOSS("토스")
    }

    private var _postId = -1
    val postId get() = _postId

    private val _payWay = MutableLiveData<Pay>()
    val payWay: LiveData<Pay> get() = _payWay

    private val previewInfoRepositoryImpl = PreviewInfoRepositoryImpl()
    private val previewListRepositoryImpl = PreviewListRepositoryImpl()

    private var _previewInfor = MutableLiveData<PreviewInfoModel>()
    val previewInfor: LiveData<PreviewInfoModel> get() = _previewInfor

    private var _previewList = MutableLiveData<List<ContentModel>>()
    val previewList: LiveData<List<ContentModel>> get() = _previewList

    fun selectPay(way: Pay) {
        when (way) {
            Pay.KAKAO -> _payWay.value = Pay.KAKAO
            Pay.NAVER -> _payWay.value = Pay.NAVER
            Pay.TOSS -> _payWay.value = Pay.TOSS
        }
    }


    fun setPostId(postId: Int) {
        _postId = postId
    }

    fun getPreviewInfo() {
        viewModelScope.launch {
            val response = previewInfoRepositoryImpl.getPreviewInfo(postId)
            _previewInfor.value = response.data
        }
    }

    fun getPreviewList() {
        viewModelScope.launch {
            val response = previewListRepositoryImpl.getPreviewList(postId)
            _previewList.value = response.data
        }
    }
}