package co.kr.bemyplan.domain.repository

import co.kr.bemyplan.domain.model.list.PlanList

interface SuggestListRepository {
    suspend fun fetchSuggestList(size: Int): PlanList
    suspend fun fetchMoreSuggestList(size: Int, lastPlanId: Int): PlanList
}