package co.kr.bemyplan.domain.repository

import co.kr.bemyplan.domain.model.list.PlanList

interface MyPlanRepository {
    suspend fun fetchMyPlanList(size: Int, sort: String) : PlanList
    suspend fun fetchMoreMyPlanList(size: Int, sort: String, lastPlanId: Int) : PlanList
}