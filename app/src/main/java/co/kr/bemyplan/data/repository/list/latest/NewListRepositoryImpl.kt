package co.kr.bemyplan.data.repository.list.latest

import co.kr.bemyplan.data.api.NewListService
import co.kr.bemyplan.data.entity.list.ResponseNewList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NewListRepositoryImpl @Inject constructor(
    private val service: NewListService
) : NewListRepository {
    override suspend fun getNewList(page: Int, pageSize: Int): ResponseNewList {
        return withContext(Dispatchers.IO) {
            service.getNewList(page, pageSize)
        }
    }
}