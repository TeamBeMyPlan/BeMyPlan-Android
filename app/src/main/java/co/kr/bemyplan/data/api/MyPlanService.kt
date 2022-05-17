package co.kr.bemyplan.data.api

import co.kr.bemyplan.data.entity.main.myplan.ResponseMyPlan
import retrofit2.http.GET
import retrofit2.http.Query

interface MyPlanService {
    @GET("/v1/plan/orders")
    suspend fun fetchMyPlanList(
        @Query("size") size: Int,
        @Query("sort", encoded = true) sort: String
    ): ResponseMyPlan

    @GET("/v1/plan/orders")
    suspend fun fetchMoreMyPlanList(
        @Query("size") size: Int,
        @Query("sort", encoded = true) sort: String,
        @Query("lastPlanId") lastPlanId: Int
    ): ResponseMyPlan
}