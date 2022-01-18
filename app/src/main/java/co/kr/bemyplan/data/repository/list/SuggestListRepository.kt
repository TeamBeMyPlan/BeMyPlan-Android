package co.kr.bemyplan.data.repository.list

import co.kr.bemyplan.data.entity.list.ResponseSuggestList

interface SuggestListRepository {
    suspend fun getSuggestList(page: Int): ResponseSuggestList
}