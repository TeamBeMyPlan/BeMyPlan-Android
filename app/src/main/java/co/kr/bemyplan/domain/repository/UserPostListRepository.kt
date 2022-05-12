package co.kr.bemyplan.domain.repository

import co.kr.bemyplan.domain.model.list.PlanList

interface UserPostListRepository {
    suspend fun fetchUserPlanList(
        size: Int,
        sort: String,
        userId: Int
    ): PlanList

    suspend fun fetchMoreUserPlanList(
        size: Int,
        sort: String,
        userId: Int,
        lastPlanId: Int
    ): PlanList
}