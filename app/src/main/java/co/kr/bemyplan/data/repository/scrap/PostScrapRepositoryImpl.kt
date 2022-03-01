package co.kr.bemyplan.data.repository.scrap

import co.kr.bemyplan.data.api.ApiService
import co.kr.bemyplan.data.api.PostScrapService
import co.kr.bemyplan.data.entity.main.scrap.ResponseScrap
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PostScrapRepositoryImpl @Inject constructor(
    private val service: PostScrapService
): PostScrapRepository {
    override suspend fun postScrap(postId: Int): ResponseScrap {
        return withContext(Dispatchers.IO) {
            service.postScrap(postId)
        }
    }
}