package co.kr.bemyplan.data.repository.main.myplan

import co.kr.bemyplan.data.api.ApiService
import co.kr.bemyplan.data.entity.main.myplan.ResponseMyPlan
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MyPlanRepositoryImpl: MyPlanRepository {
    override suspend fun getMyPlan(user_id: String, page: Int, pageSize: Int): ResponseMyPlan {
        return withContext(Dispatchers.IO) {
            ApiService.myPlanService.getMyPlan(user_id, page, pageSize)
        }
    }
}