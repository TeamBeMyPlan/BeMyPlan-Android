package co.kr.bemyplan.data.repository.list.new

import co.kr.bemyplan.data.api.ApiService
import co.kr.bemyplan.data.entity.list.ResponseNewList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NewListRepositoryImpl : NewListRepository {
    override suspend fun getNewList(page: Int, pageSize: Int): ResponseNewList {
        return withContext(Dispatchers.IO) {
            ApiService.newListService.getNewList(page, pageSize)
        }
    }
}