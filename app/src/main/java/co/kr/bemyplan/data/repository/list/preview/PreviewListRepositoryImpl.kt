package co.kr.bemyplan.data.repository.list.preview

import co.kr.bemyplan.data.api.ApiService
import co.kr.bemyplan.data.entity.purchase.before.ResponsePreviewList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PreviewListRepositoryImpl : PreviewListRepository {
    override suspend fun getPreviewList(postId: Int): ResponsePreviewList {
        return withContext(Dispatchers.IO) {
            ApiService.previewListService.getPreviewList(postId)
        }
    }
}