package co.kr.bemyplan.data.repository.list.suggest

import co.kr.bemyplan.data.api.ApiService
import co.kr.bemyplan.data.entity.list.ResponseSuggestList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SuggestListRepositoryImpl : SuggestListRepository {
    override suspend fun getSuggestList(
        page: Int,
        pageSize: Int
    ): ResponseSuggestList {
        return withContext(Dispatchers.IO) {
            ApiService.suggestListService.getSuggestList(page, pageSize)
        }
    }
}