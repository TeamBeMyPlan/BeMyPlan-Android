package co.kr.bemyplan.data.api

import co.kr.bemyplan.data.entity.main.home.ResponseHomeData
import retrofit2.Call
import retrofit2.http.GET

interface HomeSuggestService {
    @GET("/api/v1/post/recommendation?page=1&pageSize=5")
    fun getSuggestData(): Call<ResponseHomeData>
}