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

class BeforeChargingViewModel: ViewModel() {
    private var _postId = -1
    val postId get() = _postId

    private val previewInfoRepositoryImpl = PreviewInfoRepositoryImpl()
    private val previewListRepositoryImpl = PreviewListRepositoryImpl()

    private var _previewInfo = MutableLiveData<PreviewInfoModel>()
    val previewInfo: LiveData<PreviewInfoModel> get() = _previewInfo

    private var _previewList = MutableLiveData<List<ContentModel>>()
    val previewList: LiveData<List<ContentModel>> get() = _previewList

    fun setPostId(postId: Int) {
        _postId = postId
    }

    fun getPreviewInfo() {
        viewModelScope.launch {
            val response = previewInfoRepositoryImpl.getPreviewInfo(postId)
            _previewInfo.value = response.data
        }
    }

    fun getPreviewList() {
        viewModelScope.launch {
            val response = previewListRepositoryImpl.getPreviewList(postId)
            _previewList.value = response.data
        }
    }
}