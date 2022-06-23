package co.kr.bemyplan.data.api

import co.kr.bemyplan.data.entity.main.home.ResponseHomeData
import retrofit2.http.GET
import retrofit2.http.Query

interface HomeNewService {
    @GET("v1/plans")
    suspend fun getNewData(
        @Query("size") size: Int,
        @Query("sort", encoded = true) sort: String
    ): ResponseHomeData
}