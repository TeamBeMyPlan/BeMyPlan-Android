package co.kr.bemyplan.data.repository.main.myplan

import co.kr.bemyplan.data.api.MyPlanService
import co.kr.bemyplan.data.entity.main.myplan.ResponseMyPlan
import co.kr.bemyplan.domain.model.list.PlanList
import co.kr.bemyplan.domain.repository.MyPlanRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout
import javax.inject.Inject

class MyPlanRepositoryImpl @Inject constructor(
    private val service: MyPlanService,
    private val coroutineDispatcher: CoroutineDispatcher
): MyPlanRepository {
    override suspend fun fetchMyPlanList(size: Int, sort: String): PlanList {
        return withContext(coroutineDispatcher) {
            service.fetchMyPlanList(size, sort).data
        }
    }

    override suspend fun fetchMoreMyPlanList(size: Int, sort: String, lastPlanId: Int): PlanList {
        return withContext(coroutineDispatcher) {
            service.fetchMoreMyPlanList(size, sort, lastPlanId).data
        }
    }
}