package co.kr.bemyplan.data.api

import co.kr.bemyplan.data.entity.main.scrap.ResponseEmptyScrapList
import retrofit2.http.GET

interface EmptyScrapListService {
    @GET("api/v1/post/random")
    suspend fun getEmptyScrapList(): ResponseEmptyScrapList
}