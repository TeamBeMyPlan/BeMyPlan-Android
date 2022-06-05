package co.kr.bemyplan.data.repository.main.myplan

import co.kr.bemyplan.data.api.MyPlanService
import co.kr.bemyplan.domain.model.main.myplan.MyPlanData
import co.kr.bemyplan.domain.repository.MyPlanRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MyPlanRepositoryImpl @Inject constructor(
    private val myPlanService: MyPlanService,
    private val coroutineDispatcher: CoroutineDispatcher
) : MyPlanRepository {
    override suspend fun getMyPlan(size: Int): MyPlanData {
        return withContext(coroutineDispatcher) {
            myPlanService.getMyPlan(size).data
        }
    }

    override suspend fun getMoreMyPlan(size: Int, lastPlanId: Int): MyPlanData {
        return withContext(coroutineDispatcher) {
            myPlanService.getMoreMyPlan(size, lastPlanId).data
        }
    }


}