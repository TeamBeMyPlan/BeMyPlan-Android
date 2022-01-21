package co.kr.bemyplan.data.api

import co.kr.bemyplan.data.entity.purchase.after.ResponseAfterPost
import retrofit2.Call
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.Path

interface WithdrawService {
    @HTTP(method = "DELETE", path = "v1/auth/withdraw", hasBody = true)
    fun getPost(
        @Path("post_id") post_id: Int,
    ): Call<ResponseAfterPost>
}