package co.kr.bemyplan.domain.repository

import co.kr.bemyplan.domain.model.list.PlanList

interface LocationListRepository {
    suspend fun getLocationList(region: String, size: Int, sort: String): PlanList
    suspend fun getMoreLocationList(region: String, size: Int, sort: String, lastPlanId: Int): PlanList
}