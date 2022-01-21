package co.kr.bemyplan.data.repository.main.scrap

import co.kr.bemyplan.data.api.ApiService
import co.kr.bemyplan.data.entity.main.scrap.ResponseScrap
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PostScrapRepositoryImpl: PostScrapRepository {
    override suspend fun postScrap(postId: Int): ResponseScrap {
        return withContext(Dispatchers.IO) {
            ApiService.postScrapService.postScrap(postId)
        }
    }
}