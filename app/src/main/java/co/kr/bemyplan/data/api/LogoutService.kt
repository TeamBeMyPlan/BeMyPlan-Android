package co.kr.bemyplan.data.api

import co.kr.bemyplan.data.entity.main.myplan.logout.ResponseLogout
import retrofit2.http.POST

interface LogoutService {
    @POST("/v1/logout")
    suspend fun postLogout() : ResponseLogout
}