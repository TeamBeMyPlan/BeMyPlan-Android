package co.kr.bemyplan.data.repository.list

import co.kr.bemyplan.data.entity.purchase.before.ResponsePreviewInfo

interface PreviewInfoRepository {
    suspend fun getPreviewInfo(post_id: Int): ResponsePreviewInfo
}