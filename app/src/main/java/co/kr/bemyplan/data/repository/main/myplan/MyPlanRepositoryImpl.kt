package co.kr.bemyplan.data.repository.main.myplan

import co.kr.bemyplan.data.api.ApiService
import co.kr.bemyplan.data.api.MyPlanService
import co.kr.bemyplan.data.entity.main.myplan.ResponseMyPlan
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MyPlanRepositoryImpl @Inject constructor(
    private val service: MyPlanService
): MyPlanRepository {
    override suspend fun getMyPlan(page: Int, pageSize: Int): ResponseMyPlan {
        return withContext(Dispatchers.IO) {
            service.getMyPlan(page, pageSize)
        }
    }
}