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
    override suspend fun getScrapList(
        sort: String
    ): PlanList {
        return withContext(coroutineDispatcher) {
            service.getScrapList(
                10,
                sort
            ).data
        }
    }

    override suspend fun getEmptyScrapList(): ResponseEmptyScrapList {
        return withContext(Dispatchers.IO) {
            service.getEmptyScrapList()
        }
    }
}