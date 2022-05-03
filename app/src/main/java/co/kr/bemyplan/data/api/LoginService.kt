package co.kr.bemyplan.data.api

import co.kr.bemyplan.data.entity.login.login.RequestLogin
import co.kr.bemyplan.data.entity.login.login.ResponseLogin
import co.kr.bemyplan.data.entity.login.check.ResponseDuplicatedNickname
import co.kr.bemyplan.data.entity.login.signup.RequestSignUp
import co.kr.bemyplan.data.entity.login.signup.ResponseSignUp
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface LoginService {
    @POST("/v1/login")
    suspend fun postLogin(
        @Body body: RequestLogin
    ): ResponseLogin

    @GET("/v1/user/name/check")
    suspend fun postDuplicatedNickname(
        @Query("nickname") nickname: String
    ): ResponseDuplicatedNickname

    @POST("/v1/signup")
    suspend fun postSignUp(
        @Body body: RequestSignUp
    ): ResponseSignUp
}