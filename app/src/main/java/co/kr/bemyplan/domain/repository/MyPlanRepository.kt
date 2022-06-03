package co.kr.bemyplan.domain.repository

import co.kr.bemyplan.domain.model.main.myplan.MyPlanData

interface MyPlanRepository {
    suspend fun getMyPlan(size: Int): MyPlanData
    suspend fun getMoreMyPlan(size: Int, lastPlanId: Int): MyPlanData
}