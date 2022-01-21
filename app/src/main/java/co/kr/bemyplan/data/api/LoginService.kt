package co.kr.bemyplan.data.api

import co.kr.bemyplan.data.entity.login.RequestLogin
import co.kr.bemyplan.data.entity.login.ResponseLogin
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginService {
    @POST("api/v1/auth/login")
    suspend fun postLogin(
        @Body body: RequestLogin
    ): ResponseLogin
}