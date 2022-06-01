package co.kr.bemyplan.data.api

import co.kr.bemyplan.data.entity.purchase.after.MoveInfo.ResponseMoveInfo
import co.kr.bemyplan.data.entity.purchase.after.ResponsePlanDetail
import retrofit2.http.GET
import retrofit2.http.Path

interface PlanDetailService {
    @GET("/v1/plan/{planId}")
    suspend fun fetchPlanDetail(
        @Path("planId") planId: Int,
    ): ResponsePlanDetail
}