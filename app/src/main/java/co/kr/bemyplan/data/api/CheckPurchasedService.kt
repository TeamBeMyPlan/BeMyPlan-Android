package co.kr.bemyplan.data.api

import co.kr.bemyplan.data.entity.purchase.check.ResponseCheckPurchased
import retrofit2.http.GET
import retrofit2.http.Path

interface CheckPurchasedService {
    @GET("v1/plan/order/{planId}")
    suspend fun checkPurchased(
        @Path("planId") planId: Int
    ): ResponseCheckPurchased
}