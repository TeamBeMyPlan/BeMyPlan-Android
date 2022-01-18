package co.kr.bemyplan.data.api

import co.kr.bemyplan.data.entity.main.home.ResponseHomePopularData
import retrofit2.Call
import retrofit2.http.GET

interface HomePopularService {
    @GET("/api/v1/post/popular")
    fun getPopularData(): Call<ResponseHomePopularData>
}