package co.kr.bemyplan.data.api

import co.kr.bemyplan.data.entity.purchase.before.ResponsePreviewPlan
import retrofit2.http.GET
import retrofit2.http.Path

interface PreviewService {
    @GET("/api/v1/plan/{planId}/preview")
    suspend fun fetchPreviewPlan(
        @Path("planId") planId: Int
    ): ResponsePreviewPlan
}