package co.kr.bemyplan.data.api

import co.kr.bemyplan.data.entity.main.myplan.ResponseMyPlan
import retrofit2.http.GET
import retrofit2.http.Query

interface MyPlanService {
    @GET("v1/plan/orders")
    suspend fun getMyPlan(
        @Query("size") size: Int
    ): ResponseMyPlan

    @GET("v1/plan/orders")
    suspend fun getMoreMyPlan(
        @Query("size") size: Int,
        @Query("lastPlanId") lastPlanId: Int
    ): ResponseMyPlan
}