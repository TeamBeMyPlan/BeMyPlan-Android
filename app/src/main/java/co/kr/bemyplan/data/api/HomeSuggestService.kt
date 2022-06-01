package co.kr.bemyplan.data.api

import co.kr.bemyplan.data.entity.main.home.ResponseHomeData
import retrofit2.Call
import retrofit2.http.GET

interface HomeSuggestService {
    @GET("v1/plans/bemyplanPick?size=5&sort=id,desc")
    suspend fun getSuggestData(): ResponseHomeData
}