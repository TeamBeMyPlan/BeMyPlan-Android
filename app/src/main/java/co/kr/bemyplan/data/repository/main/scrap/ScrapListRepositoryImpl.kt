package co.kr.bemyplan.data.repository.main.scrap

import co.kr.bemyplan.data.api.ApiService
import co.kr.bemyplan.data.entity.main.scrap.ResponseScrapList

class ScrapListRepositoryImpl : ScrapListRepository {
    override suspend fun getScrapList(
        user_id: String,
        page: Int,
        pageSize: Int,
        sort: String
    ): ResponseScrapList {
        return ApiService.scrapListService.getScrapList(user_id, page, pageSize, sort)
    }
}