package co.kr.bemyplan.data.repository.purchase.after

import co.kr.bemyplan.data.api.PlanDetailService
import co.kr.bemyplan.domain.model.purchase.after.PlanDetail
import co.kr.bemyplan.domain.repository.PlanDetailRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PlanDetailRepositoryImpl @Inject constructor(
    private val service: PlanDetailService,
    private val coroutineDispatcher: CoroutineDispatcher
) : PlanDetailRepository {
    override suspend fun fetchPlanDetail(planId: Int): PlanDetail {
        return withContext(coroutineDispatcher) {
            service.fetchPlanDetail(planId).data
        }
    }
}