package co.kr.bemyplan.data.repository.list

import co.kr.bemyplan.data.api.ApiService
import co.kr.bemyplan.data.entity.list.ResponseNewList

class NewListRepositoryImpl: NewListRepository {
    override suspend fun getNewList(page: Int): ResponseNewList {
        return ApiService.newListService.getNewList(page)
    }
}