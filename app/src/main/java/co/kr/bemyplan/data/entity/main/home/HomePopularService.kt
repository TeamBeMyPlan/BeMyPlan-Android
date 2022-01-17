package co.kr.bemyplan.data.entity.main.home

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET

interface HomePopularService {
    @GET()
    fun getPopularData(): Call<ResponseHomePopularData>
}