package co.kr.bemyplan.data.repository.purchase.preview

import co.kr.bemyplan.data.entity.purchase.before.ResponsePreviewInfo

interface PreviewInfoRepository {
    suspend fun getPreviewInfo(postId: Int): ResponsePreviewInfo
}