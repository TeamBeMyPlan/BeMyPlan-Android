package co.kr.bemyplan.data.repository.main.scrap

import co.kr.bemyplan.data.api.ApiService
import co.kr.bemyplan.data.entity.main.scrap.ResponseEmptyScrapList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class EmptyScrapListRepositoryImpl : EmptyScrapListRepository {
    override suspend fun getEmptyScrapList(): ResponseEmptyScrapList {
        return withContext(Dispatchers.IO) {
            ApiService.emptyScrapListService.getEmptyScrapList()
        }
    }
}