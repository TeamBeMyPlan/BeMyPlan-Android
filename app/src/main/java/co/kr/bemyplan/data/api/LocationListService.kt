package co.kr.bemyplan.data.api

import co.kr.bemyplan.data.entity.list.ResponseLocationList
import com.google.gson.annotations.SerializedName
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface LocationListService {
    @GET("api/v1/area/{area_id}")
    suspend fun getLocationList(
        @Path("area_id") area_id: Int,
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int,
        @Query("sort") sort: String
    ): ResponseLocationList
}