package co.kr.bemyplan.data.api

import co.kr.bemyplan.data.entity.main.scrap.ResponseScrap
import retrofit2.http.POST
import retrofit2.http.Path

interface PostScrapService {
    @POST("api/v1/scrap/{postId}")
    suspend fun postScrap(
        @Path("postId") postId: Int
    ): ResponseScrap
}