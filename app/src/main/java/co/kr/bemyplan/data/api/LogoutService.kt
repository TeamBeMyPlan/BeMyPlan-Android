package co.kr.bemyplan.data.api

import co.kr.bemyplan.data.entity.logout.RequestSignOut
import co.kr.bemyplan.data.entity.logout.ResponseLogout
import co.kr.bemyplan.data.entity.logout.ResponseSignOut
import retrofit2.http.Body
import retrofit2.http.HTTP
import retrofit2.http.POST

interface LogoutService {
    @POST("v1/logout")
    suspend fun postLogout(): ResponseLogout

    @HTTP(method = "DELETE", path = "v1/signout", hasBody = true)
    suspend fun deleteSignOut(
        @Body requestBody: RequestSignOut
    ): ResponseSignOut
}