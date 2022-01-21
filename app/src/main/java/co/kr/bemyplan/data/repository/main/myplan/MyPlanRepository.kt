package co.kr.bemyplan.data.repository.main.myplan

import co.kr.bemyplan.data.entity.main.myplan.ResponseMyPlan

interface MyPlanRepository {
    suspend fun getMyPlan(page: Int, pageSize: Int): ResponseMyPlan
}