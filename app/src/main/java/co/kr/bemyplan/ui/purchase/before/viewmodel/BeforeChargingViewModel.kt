package co.kr.bemyplan.ui.purchase.before.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.kr.bemyplan.data.entity.purchase.before.ContentModel
import co.kr.bemyplan.data.entity.purchase.before.PreviewInfoModel
import co.kr.bemyplan.data.repository.list.preview.PreviewInfoRepositoryImpl
import co.kr.bemyplan.data.repository.list.preview.PreviewListRepositoryImpl
import co.kr.bemyplan.data.repository.main.scrap.PostScrapRepositoryImpl
import kotlinx.coroutines.launch


class BeforeChargingViewModel : ViewModel() {

    enum class Pay(val brand: String) {
        NAVER("네이버페이"), KAKAO("카카오페이"), TOSS("토스"), NULL("null")
    }

    private var _postId = -1
    val postId get() = _postId

    private val _payWay = MutableLiveData<Pay>(Pay.NULL)
    private var _isScraped = MutableLiveData<Boolean>()
    val isScraped: LiveData<Boolean> get() = _isScraped

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

    fun postScrap() {
        val postScrapRepositoryImpl = PostScrapRepositoryImpl()
        viewModelScope.launch {
            try {
                val response = postScrapRepositoryImpl.postScrap(postId)
                if (_isScraped.value != null) {
                    _isScraped.value = !_isScraped.value!!
                }
            } catch (e: retrofit2.HttpException) {
                Log.e(
                    "mlog: BeforeChargingViewModel::postScrap error handling",
                    e.code().toString()
                )
            } catch (t: Throwable) {
                Log.e(
                    "mlog: BeforeChargingViewModel::postScrap error handling",
                    t.message.toString()
                )
            }
        }
    }

    fun setIsScraped(flag: Boolean?) {
        if (flag != null) {
            _isScraped.value = flag
            Log.d("mlog: BeforeChargingViewModel::setIsScraped", isScraped.value.toString())
        }
    }

    fun setPostId(postId: Int) {
        _postId = postId
    }

    fun getPreviewInfo() {
        viewModelScope.launch {
            try {
                val response = previewInfoRepositoryImpl.getPreviewInfo(postId)
                _previewInfor.value = response.data
            } catch (e: retrofit2.HttpException) {
                Log.e(
                    "mlog: BeforeChargingViewModel::getPreviewInfo error handling",
                    e.code().toString()
                )
            } catch (t: Throwable) {
                Log.e(
                    "mlog: BeforeChargingViewModel::getPreviewInfo error handling",
                    t.message.toString()
                )
            }
        }
    }

    fun getPreviewList() {
        viewModelScope.launch {
            try {
                val response = previewListRepositoryImpl.getPreviewList(postId)
                _previewList.value = response.data
            } catch (e: retrofit2.HttpException) {
                Log.e(
                    "mlog: BeforeChargingViewModel::getPreviewList error handling",
                    e.code().toString()
                )
            } catch (t: Throwable) {
                Log.e(
                    "mlog: BeforeChargingViewModel::getPreviewList error handling",
                    t.message.toString()
                )
            }
        }
    }
}