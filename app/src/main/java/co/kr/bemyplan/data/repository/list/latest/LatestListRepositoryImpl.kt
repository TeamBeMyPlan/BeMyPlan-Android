package co.kr.bemyplan.data.repository.list.latest

import co.kr.bemyplan.data.api.LatestListService
import co.kr.bemyplan.data.entity.list.ResponseLatestList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LatestListRepositoryImpl @Inject constructor(
    private val service: LatestListService
) : LatestListRepository {
    override suspend fun getNewList(page: Int, pageSize: Int): ResponseLatestList {
        return withContext(Dispatchers.IO) {
            service.getNewList(page, pageSize)
        }
    }
}