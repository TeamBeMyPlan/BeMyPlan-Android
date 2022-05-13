package co.kr.bemyplan.data.api

import co.kr.bemyplan.data.entity.purchase.before.RequestPurchase
import co.kr.bemyplan.data.entity.purchase.before.ResponsePurchase
import retrofit2.http.Body
import retrofit2.http.POST

interface PurchaseService {
    @POST("/v1/plan/order")
    suspend fun purchase(
        @Body requestBody: RequestPurchase
    ): ResponsePurchase
}