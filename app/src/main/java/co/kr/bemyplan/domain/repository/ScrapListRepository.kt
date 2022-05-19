package co.kr.bemyplan.domain.repository

import co.kr.bemyplan.data.entity.main.scrap.ResponseEmptyScrapList
import co.kr.bemyplan.domain.model.list.PlanList

interface ScrapListRepository {
    suspend fun getScrapList(
        sort: String
    ): PlanList

    suspend fun getEmptyScrapList(): ResponseEmptyScrapList
}