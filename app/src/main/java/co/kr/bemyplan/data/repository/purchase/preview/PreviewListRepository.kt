package co.kr.bemyplan.data.repository.purchase.preview

import co.kr.bemyplan.data.entity.purchase.before.ResponsePreviewList

interface PreviewListRepository {
    suspend fun getPreviewList(postId: Int): ResponsePreviewList
}