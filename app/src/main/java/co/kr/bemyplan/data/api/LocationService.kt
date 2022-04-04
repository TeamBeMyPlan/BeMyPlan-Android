package co.kr.bemyplan.data.api

import co.kr.bemyplan.data.entity.main.location.ResponseLocationData
import retrofit2.Call
import retrofit2.http.GET

interface LocationService {
    @GET("/api/v1/plan/regions")
    fun getLocationData() : Call<ResponseLocationData>
}