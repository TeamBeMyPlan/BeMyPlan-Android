package co.kr.bemyplan.data.api

import co.kr.bemyplan.data.entity.main.scrap.ResponseEmptyScrapList
import co.kr.bemyplan.data.entity.main.scrap.ResponseScrapList
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ListService {
    @GET("/api/v1/scrap")
    suspend fun getScrapList(
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int,
        @Query("sort") sort: String
    ): ResponseScrapList

    @GET("api/v1/post/random")
    suspend fun getEmptyScrapList(): ResponseEmptyScrapList
}