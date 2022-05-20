package co.kr.bemyplan.data.repository.scrap

import co.kr.bemyplan.data.api.ScrapService
import co.kr.bemyplan.domain.repository.ScrapRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ScrapRepositoryImpl @Inject constructor(
    private val service: ScrapService,
    private val coroutineDispatcher: CoroutineDispatcher
) : ScrapRepository {
    override suspend fun postScrap(planId: Int): Boolean {
        return withContext(coroutineDispatcher) {
            service.postScrap(planId).toBoolean()
        }
    }

    override suspend fun deleteScrap(planId: Int): Boolean {
        return withContext(coroutineDispatcher) {
            service.deleteScrap(planId).toBoolean()
        }
    }
}