package co.kr.bemyplan.data.api

import co.kr.bemyplan.data.entity.purchase.after.ResponseAfterPost
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface AfterPostService {
    @GET("api/v1/post/{post_id}")
    fun getPost(
        @Path("post_id") post_id: Int,
    ): ResponseAfterPost
}