package co.kr.bemyplan.data.api

import co.kr.bemyplan.data.entity.main.scrap.ResponseEmptyScrapList
import co.kr.bemyplan.data.entity.main.scrap.ResponseScrapList
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ScrapListService {
    @GET("/v1/plan/bookmark")
    suspend fun getScrapList(
        @Query("size") page: Int,
        @Query("sort", encoded = true) sort: String
    ): ResponseScrapList

    @GET("/v1/post/random")
    suspend fun getEmptyScrapList(): ResponseEmptyScrapList
}