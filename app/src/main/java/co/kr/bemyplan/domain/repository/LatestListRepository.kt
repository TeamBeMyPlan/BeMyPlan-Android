package co.kr.bemyplan.domain.repository

import co.kr.bemyplan.domain.model.list.PlanList

interface LatestListRepository {
    suspend fun fetchLatestList(size: Int, sort: String): PlanList
    suspend fun fetchMoreLatestList(size: Int, sort: String, lastPlanId: Int): PlanList
}