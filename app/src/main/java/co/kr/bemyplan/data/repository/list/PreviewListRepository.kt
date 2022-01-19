package co.kr.bemyplan.data.repository.list

import co.kr.bemyplan.data.entity.purchase.before.ResponsePreviewList

interface PreviewListRepository {
    suspend fun getPreviewList(post_id: Int): ResponsePreviewList
}