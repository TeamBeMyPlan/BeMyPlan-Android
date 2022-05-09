package co.kr.bemyplan.data.api

import co.kr.bemyplan.data.entity.purchase.after.MoveInfo.ResponseMoveInfo
import retrofit2.http.GET
import retrofit2.http.Path

interface MoveInfoService {
    @GET("v1/plan/{planId}/moveInfo")
    suspend fun fetchMoveInfo(
        @Path("planId") planId: Int,
    ): ResponseMoveInfo
}