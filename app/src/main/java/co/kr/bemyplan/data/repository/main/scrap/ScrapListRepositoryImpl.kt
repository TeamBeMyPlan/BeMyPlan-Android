package co.kr.bemyplan.data.repository.main.scrap

import co.kr.bemyplan.data.api.ScrapListService
import co.kr.bemyplan.data.entity.main.scrap.ResponseEmptyScrapList
import co.kr.bemyplan.domain.model.list.PlanList
import co.kr.bemyplan.domain.repository.ScrapListRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ScrapListRepositoryImpl @Inject constructor(
    private val service: ScrapListService,
    private val coroutineDispatcher: CoroutineDispatcher
) : ScrapListRepository {
    override suspend fun fetchDefaultScrapList(): PlanList =
        withContext(coroutineDispatcher) {
            service.fetchDefaultScrapList(10).data
        }

    override suspend fun fetchQueryScrapList(
        sort: String
    ): PlanList {
        return withContext(coroutineDispatcher) {
            service.fetchQueryScrapList(
                10,
                sort
            ).data
        }
    }

    override suspend fun fetchEmptyScrapList(): ResponseEmptyScrapList {
        return withContext(coroutineDispatcher) {
            service.getEmptyScrapList()
        }
    }

    override suspend fun fetchDefaultMoreScrapList(size: Int, lastScrapId: Int): PlanList =
        withContext(coroutineDispatcher) {
            service.fetchDefaultMoreScrapList(size, lastScrapId).data
        }

    override suspend fun fetchQueryMoreScrapList(
        size: Int,
        lastScrapId: Int,
        sort: String
    ): PlanList =
        withContext(coroutineDispatcher) {
            service.fetchQueryMoreScrapList(size, lastScrapId, sort).data
        }
}