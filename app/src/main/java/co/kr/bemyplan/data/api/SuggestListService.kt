package co.kr.bemyplan.data.api

import co.kr.bemyplan.data.entity.list.ResponseSuggestList
import retrofit2.http.GET
import retrofit2.http.Query

interface SuggestListService {
    @GET("api/v1/post/suggest/")
    suspend fun getSuggestList(
        @Query("page") page: Int
    ): ResponseSuggestList
}