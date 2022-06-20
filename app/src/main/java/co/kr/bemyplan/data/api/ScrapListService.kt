package co.kr.bemyplan.data.api

import co.kr.bemyplan.data.entity.main.scrap.ResponseEmptyScrapList
import co.kr.bemyplan.data.entity.main.scrap.ResponseScrapList
import retrofit2.http.GET
import retrofit2.http.Query

interface ScrapListService {
    @GET("v1/plan/bookmark")
    suspend fun fetchDefaultScrapList(
        @Query("size") size: Int
    ): ResponseScrapList

    @GET("v1/plan/bookmark")
    suspend fun fetchQueryScrapList(
        @Query("size") page: Int,
        @Query("sort", encoded = true) sort: String
    ): ResponseScrapList

    @GET("v1/post/random")
    suspend fun getEmptyScrapList(): ResponseEmptyScrapList

    @GET("v1/plan/bookmark")
    suspend fun fetchDefaultMoreScrapList(
        @Query("size") size: Int,
        @Query("lastScrapId") lastScrapId: Int
    ): ResponseScrapList

    @GET("v1/plan/bookmark")
    suspend fun fetchQueryMoreScrapList(
        @Query("size") size: Int,
        @Query("lastScrapId") lastScrapId: Int,
        @Query("sort", encoded = true) sort: String
    ): ResponseScrapList
}