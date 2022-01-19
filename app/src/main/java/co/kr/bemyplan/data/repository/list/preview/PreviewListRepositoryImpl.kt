package co.kr.bemyplan.data.repository.list.preview

import co.kr.bemyplan.data.api.ApiService
import co.kr.bemyplan.data.entity.purchase.before.ResponsePreviewList

class PreviewListRepositoryImpl: PreviewListRepository {
    override suspend fun getPreviewList(post_id: Int): ResponsePreviewList {
        return ApiService.previewListService.getPreviewList(post_id)
    }
}