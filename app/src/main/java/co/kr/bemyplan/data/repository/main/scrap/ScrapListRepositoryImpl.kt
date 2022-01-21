package co.kr.bemyplan.data.repository.main.scrap

import co.kr.bemyplan.data.api.ApiService
import co.kr.bemyplan.data.entity.main.scrap.ResponseScrapList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ScrapListRepositoryImpl : ScrapListRepository {
    override suspend fun getScrapList(
        page: Int,
        pageSize: Int,
        sort: String
    ): ResponseScrapList {
        return withContext(Dispatchers.IO) {
            ApiService.scrapListService.getScrapList(
                page,
                pageSize,
                sort
            )
        }
    }
}