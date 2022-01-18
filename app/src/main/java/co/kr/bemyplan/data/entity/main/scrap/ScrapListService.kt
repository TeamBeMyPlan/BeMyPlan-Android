package co.kr.bemyplan.data.entity.main.scrap

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ScrapListService {
    @GET("/api/v1/scrap/{user_id}")
    suspend fun getScrapList(
        @Path("user_id") user_id: String,
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int,
        @Query("sort") sort: String
    ): ResponseScrapList
}