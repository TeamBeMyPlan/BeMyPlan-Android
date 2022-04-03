package co.kr.bemyplan.data.api

import co.kr.bemyplan.data.entity.login.login.RequestLogin
import co.kr.bemyplan.data.entity.login.login.ResponseLogin
import co.kr.bemyplan.data.entity.login.check.RequestDuplicatedNickname
import co.kr.bemyplan.data.entity.login.check.ResponseDuplicatedNickname
import co.kr.bemyplan.data.entity.login.signup.RequestSignUp
import co.kr.bemyplan.data.entity.login.signup.ResponseSignUp
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginService {
    @POST("api/v1/login")
    suspend fun postLogin(
        @Body body: RequestLogin
    ): ResponseLogin

    @POST("api/v1/auth/check/nickname")
    suspend fun postDuplicatedNickname(
        @Body body: RequestDuplicatedNickname
    ): ResponseDuplicatedNickname

    @POST("api/v1/signup")
    suspend fun postSignUp(
        @Body body: RequestSignUp
    ): ResponseSignUp
}