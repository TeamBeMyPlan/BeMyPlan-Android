package co.kr.bemyplan.data.repository.list.userpost

import co.kr.bemyplan.data.api.ApiService
import co.kr.bemyplan.data.entity.list.ResponseUserPostList

class UserPostListRepositoryImpl : UserPostListRepository {
    override suspend fun getUserPostList(
        userId: String,
        page: Int,
        pageSize: Int,
        sort: String
    ): ResponseUserPostList {
        return ApiService.userPostListService.getUserPostList(userId, page, pageSize, sort)
    }
}