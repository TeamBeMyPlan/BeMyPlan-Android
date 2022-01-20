package co.kr.bemyplan.data.api

import co.kr.bemyplan.data.entity.main.home.ResponseHomeData
import retrofit2.Call
import retrofit2.http.GET

interface HomeNewService {
    @GET("/api/v1/post/new")
    fun getNewData(): Call<ResponseHomeData>
}