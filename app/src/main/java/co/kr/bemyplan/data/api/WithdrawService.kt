package co.kr.bemyplan.data.api

import co.kr.bemyplan.data.entity.main.myplan.RequestWithdraw
import co.kr.bemyplan.data.entity.main.myplan.ResponseWithdraw
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.HTTP

interface WithdrawService {
    @HTTP(method = "DELETE", path = "/v1/auth/withdraw", hasBody = true)
    fun deleteToken(
        @Body body: RequestWithdraw
    ): Call<ResponseWithdraw>
}