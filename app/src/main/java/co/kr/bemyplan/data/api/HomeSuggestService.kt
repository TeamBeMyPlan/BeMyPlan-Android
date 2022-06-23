package co.kr.bemyplan.data.api

import co.kr.bemyplan.data.entity.main.home.ResponseHomeData
import retrofit2.http.GET
import retrofit2.http.Query

interface HomeSuggestService {
    @GET("v1/plans/bemyplanPick")
    suspend fun getSuggestData(
        @Query("size") size: Int,
        @Query("sort", encoded = true) sort: String
    ): ResponseHomeData
}