package co.kr.bemyplan.data.repository.main.scrap

import co.kr.bemyplan.data.entity.main.scrap.ResponseEmptyScrapList
import co.kr.bemyplan.data.entity.main.scrap.ResponseScrapList

interface ScrapListRepository {
    suspend fun getScrapList(
        page: Int,
        pageSize: Int,
        sort: String
    ): ResponseScrapList

    suspend fun getEmptyScrapList(): ResponseEmptyScrapList
}