package co.kr.bemyplan.data.repository.purchase.after

import co.kr.bemyplan.data.entity.purchase.after.ResponseAfterPost

interface AfterPurchaseRepository {
    suspend fun getAfterPost(postId: Int): ResponseAfterPost
}