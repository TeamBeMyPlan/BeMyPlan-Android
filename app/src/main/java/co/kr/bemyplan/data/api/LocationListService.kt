package co.kr.bemyplan.data.api

import co.kr.bemyplan.data.entity.list.ResponseLocationList
import retrofit2.http.GET
import retrofit2.http.Query

interface LocationListService {
    @GET("/v1/plans")
    suspend fun fetchLocationList(
        @Query("region") region: String,
        @Query("size") size: Int,
        @Query("sort", encoded = true) sort: String
    ): ResponseLocationList

    @GET("/v1/plans")
    suspend fun fetchMoreLocationList(
        @Query("region") region: String,
        @Query("size") size: Int,
        @Query("sort", encoded = true) sort: String,
        @Query("lastPlanId") lastPlanId: Int
    ): ResponseLocationList
}