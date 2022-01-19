package co.kr.bemyplan.data.repository.list.suggest

import co.kr.bemyplan.data.api.ApiService
import co.kr.bemyplan.data.entity.list.ResponseSuggestList

class SuggestListRepositoryImpl: SuggestListRepository {
    override suspend fun getSuggestList(page: Int, pageSize: Int): ResponseSuggestList {
        return ApiService.suggestListService.getSuggestList(page, pageSize)
    }
}