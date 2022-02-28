package co.kr.bemyplan.data.repository.list.userpost

import co.kr.bemyplan.data.api.ApiService
import co.kr.bemyplan.data.api.UserPostListService
import co.kr.bemyplan.data.entity.list.ResponseUserPostList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserPostListRepositoryImpl @Inject constructor(
    private val service: UserPostListService
) : UserPostListRepository {
    override suspend fun getUserPostList(
        userId: Int,
        page: Int,
        pageSize: Int,
        sort: String
    ): ResponseUserPostList {
        return withContext(Dispatchers.IO) {
            service.getUserPostList(
                userId,
                page,
                pageSize,
                sort
            )
        }
    }
}