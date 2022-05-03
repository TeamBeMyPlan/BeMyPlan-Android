package co.kr.bemyplan.data.api

import co.kr.bemyplan.data.entity.list.ResponseLocationList
import com.google.gson.annotations.SerializedName
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface LocationListService {
    @GET("/v1/plans")
    suspend fun getLocationList(
        @Query("region") region: String,
        @Query("size") size: Int,
        @Query("sort", encoded = true) sort: String
    ): ResponseLocationList

    @GET("/v1/plans")
    suspend fun getMoreLocationList(
        @Query("region") region: String,
        @Query("size") size: Int,
        @Query("sort", encoded = true) sort: String,
        @Query("lastPlanId") lastPlanId: Int
    ): ResponseLocationList
}