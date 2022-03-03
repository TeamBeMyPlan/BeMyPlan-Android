package co.kr.bemyplan.data.repository.purchase.preview

import co.kr.bemyplan.data.entity.purchase.before.ResponsePreviewInfo
import co.kr.bemyplan.data.entity.purchase.before.ResponsePreviewList

interface PreviewRepository {
    suspend fun getPreviewInfo(postId: Int): ResponsePreviewInfo
    suspend fun getPreviewList(postId: Int): ResponsePreviewList
}