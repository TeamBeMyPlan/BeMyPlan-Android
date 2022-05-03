package co.kr.bemyplan.data.api

import co.kr.bemyplan.data.entity.login.login.RequestGoogleLogin
import co.kr.bemyplan.data.entity.login.login.ResponseGoogleLogin
import retrofit2.http.Body
import retrofit2.http.POST

interface GoogleLoginService {
    @POST("/oauth2/v4/token")
    suspend fun postGoogleSignInData(
        @Body body: RequestGoogleLogin
    ): ResponseGoogleLogin
}