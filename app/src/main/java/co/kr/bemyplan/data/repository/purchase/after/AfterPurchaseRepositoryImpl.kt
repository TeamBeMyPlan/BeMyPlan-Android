package co.kr.bemyplan.data.repository.purchase.after

import co.kr.bemyplan.data.api.AfterPostService
import co.kr.bemyplan.data.entity.purchase.after.ResponseAfterPost
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AfterPurchaseRepositoryImpl @Inject constructor(
    private val afterPostService: AfterPostService
) : AfterPurchaseRepository {
    override suspend fun getAfterPost(postId: Int): ResponseAfterPost {
        return withContext(Dispatchers.IO) {
            afterPostService.getPost(postId)
        }
    }
}