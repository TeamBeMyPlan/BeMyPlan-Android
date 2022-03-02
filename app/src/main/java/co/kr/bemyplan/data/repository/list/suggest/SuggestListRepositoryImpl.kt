package co.kr.bemyplan.data.repository.list.suggest

import co.kr.bemyplan.data.api.ApiService
import co.kr.bemyplan.data.api.SuggestListService
import co.kr.bemyplan.data.entity.list.ResponseSuggestList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SuggestListRepositoryImpl @Inject constructor(
    private val service: SuggestListService
) : SuggestListRepository {
    override suspend fun getSuggestList(
        page: Int,
        pageSize: Int
    ): ResponseSuggestList {
        return withContext(Dispatchers.IO) {
            service.getSuggestList(page, pageSize)
        }
    }
}