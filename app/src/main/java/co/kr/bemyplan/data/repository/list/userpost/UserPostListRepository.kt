package co.kr.bemyplan.data.repository.list.userpost

import co.kr.bemyplan.data.entity.list.ResponseUserPostList

interface UserPostListRepository {
    suspend fun getUserPostList(
        userId: String,
        page: Int,
        pageSize: Int,
        sort: String
    ): ResponseUserPostList
}