package co.kr.bemyplan.domain.repository

import co.kr.bemyplan.domain.model.list.PlanList

interface ScrapListRepository {
    suspend fun fetchDefaultScrapList(): PlanList

    suspend fun fetchQueryScrapList(sort: String): PlanList

    suspend fun fetchDefaultMoreScrapList(size: Int, lastScrapId: Int): PlanList

    suspend fun fetchQueryMoreScrapList(size: Int, lastScrapId: Int, sort: String): PlanList
}