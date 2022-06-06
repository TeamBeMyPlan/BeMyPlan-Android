package co.kr.bemyplan.data.api

import co.kr.bemyplan.data.entity.scrap.ResponseScrap
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ScrapService {
    @POST("v1/plan/scrap/{planId}")
    suspend fun postScrap(
        @Path("planId") planId: Int
    ): ResponseScrap

    @DELETE("v1/plan/scrap/{planId}")
    suspend fun deleteScrap(
        @Path("planId") planId: Int
    ): ResponseScrap

    @GET("v1/plan/order/{planId}")
    suspend fun checkScrapStatus(
        @Path("planId") planId: Int
    ): ResponseScrap
}