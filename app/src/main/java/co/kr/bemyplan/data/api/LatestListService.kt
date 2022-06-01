package co.kr.bemyplan.data.api

import co.kr.bemyplan.data.entity.list.ResponseLatestList
import retrofit2.http.GET
import retrofit2.http.Query

interface LatestListService {
    @GET("v1/plans")
    suspend fun fetchLatestList(
        @Query("size") size: Int,
        @Query("sort", encoded = true) sort: String
    ): ResponseLatestList

    @GET("v1/plans")
    suspend fun fetchMoreLatestList(
        @Query("size") size: Int,
        @Query("sort", encoded = true) sort: String,
        @Query("lastPlanId") lastPlanId: Int
    ): ResponseLatestList
}