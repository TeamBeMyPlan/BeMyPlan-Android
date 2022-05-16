package co.kr.bemyplan.data.api

import co.kr.bemyplan.data.entity.main.home.ResponseHomeData
import retrofit2.Call
import retrofit2.http.GET

interface HomeNewService {
    @GET("/v1/plans?size=10&sort=createdAt,desc")
    suspend fun getNewData() : ResponseHomeData
}