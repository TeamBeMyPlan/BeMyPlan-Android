package co.kr.bemyplan.data.api

import co.kr.bemyplan.data.entity.list.ResponseUserPlanList
import retrofit2.http.GET
import retrofit2.http.Query

interface UserPlanListService {
    @GET("/api/v1/plans")
    suspend fun fetchUserPlanList(
        @Query("size") size: Int,
        @Query("sort") sort: String,
        @Query("userId") userId: Int
    ): ResponseUserPlanList

    @GET("/api/v1/plans")
    suspend fun fetchMoreUserPlanList(
        @Query("size") size: Int,
        @Query("sort") sort: String,
        @Query("userId") userId: Int,
        @Query("lastPlanId") lastPlanId: Int
    ): ResponseUserPlanList
}