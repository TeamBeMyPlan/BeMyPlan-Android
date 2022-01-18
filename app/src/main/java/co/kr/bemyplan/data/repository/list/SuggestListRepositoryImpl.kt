package co.kr.bemyplan.data.repository.list

import co.kr.bemyplan.data.api.ApiService
import co.kr.bemyplan.data.entity.list.ResponseSuggestList

class SuggestListRepositoryImpl: SuggestListRepository {
    override suspend fun getSuggestList(page: Int): ResponseSuggestList {
        return ApiService.suggestListService.getSuggestList(page)
    }
}