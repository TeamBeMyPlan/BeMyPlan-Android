package co.kr.bemyplan.data.api

import co.kr.bemyplan.data.entity.list.ResponseUserPostList
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface UserPostListService {
    @GET("api/v1/user/{userId}/posts")
    suspend fun getUserPostList(
        @Path("userId") userId: Int,
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int,
        @Query("sort") sort: String
    ): ResponseUserPostList
}