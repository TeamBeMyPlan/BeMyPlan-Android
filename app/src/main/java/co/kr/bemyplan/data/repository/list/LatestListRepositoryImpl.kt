package co.kr.bemyplan.data.repository.list

import co.kr.bemyplan.data.api.LatestListService
import co.kr.bemyplan.domain.model.list.PlanList
import co.kr.bemyplan.domain.repository.LatestListRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LatestListRepositoryImpl @Inject constructor(
    private val service: LatestListService,
    private val coroutineDispatcher: CoroutineDispatcher
) : LatestListRepository {
    override suspend fun fetchLatestList(size: Int, sort: String): PlanList {
        return withContext(coroutineDispatcher) {
            service.fetchLatestList(size, sort).data
        }
    }

    override suspend fun fetchMoreLatestList(size: Int, sort: String, lastPlanId: Int): PlanList {
        return withContext(coroutineDispatcher) {
            service.fetchMoreLatestList(size, sort, lastPlanId).data
        }
    }
}