package co.kr.bemyplan.data.api

import co.kr.bemyplan.data.entity.main.myplan.ResponseMyPlan
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MyPlanService {
    @GET("api/v1/order/{user_id}")
    suspend fun getMyPlan(
        @Path("user_id") user_id: String,
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int
    ): ResponseMyPlan
}