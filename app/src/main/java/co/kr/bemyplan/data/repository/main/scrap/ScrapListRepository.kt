package co.kr.bemyplan.data.repository.main.scrap

import co.kr.bemyplan.data.entity.main.scrap.ResponseScrapList

interface ScrapListRepository {
    suspend fun getScrapList(
        user_id: String,
        page: Int,
        pageSize: Int,
        sort: String
    ): ResponseScrapList
}