package co.kr.bemyplan.data.repository.main.scrap

import co.kr.bemyplan.data.api.ScrapListService
import co.kr.bemyplan.data.entity.main.scrap.ResponseEmptyScrapList
import co.kr.bemyplan.data.entity.main.scrap.ResponseScrapList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ScrapListRepositoryImpl @Inject constructor(
    private val service: ScrapListService
) : ScrapListRepository {
    override suspend fun getScrapList(
        page: Int,
        pageSize: Int,
        sort: String
    ): ResponseScrapList {
        return withContext(Dispatchers.IO) {
            service.getScrapList(
                page,
                pageSize,
                sort
            )
        }
    }

    override suspend fun getEmptyScrapList(): ResponseEmptyScrapList {
        return withContext(Dispatchers.IO) {
            service.getEmptyScrapList()
        }
    }
}