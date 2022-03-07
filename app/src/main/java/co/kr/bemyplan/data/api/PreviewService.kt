package co.kr.bemyplan.data.api

import co.kr.bemyplan.data.entity.purchase.before.ResponsePreviewInfo
import co.kr.bemyplan.data.entity.purchase.before.ResponsePreviewList
import retrofit2.http.GET
import retrofit2.http.Path

interface PreviewService {
    @GET("api/v1/post/{post_id}/preview/tag")
    suspend fun getPreviewInfo(
        @Path("post_id") postId: Int
    ): ResponsePreviewInfo

    @GET("api/v1/post/{post_id}/preview")
    suspend fun getPreviewList(
        @Path("post_id") postId: Int
    ): ResponsePreviewList
}