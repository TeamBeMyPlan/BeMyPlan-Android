package co.kr.bemyplan.data.api

import co.kr.bemyplan.data.entity.list.ResponseNewList
import retrofit2.http.GET
import retrofit2.http.Query

interface NewListService {
    @GET("api/v1/post/new")
    suspend fun getNewList(
        @Query("page") page: Int,
    ): ResponseNewList
}