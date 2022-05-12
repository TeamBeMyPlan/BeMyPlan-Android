package co.kr.bemyplan.data.api

import co.kr.bemyplan.data.entity.list.ResponseSuggestList
import retrofit2.http.GET
import retrofit2.http.Query

interface SuggestListService {
    @GET("/v1/plans/bemyplanPick")
    suspend fun fetchSuggestList(
        @Query("size") size: Int
    ): ResponseSuggestList

    @GET("/v1/plans/bemyplanPick")
    suspend fun fetchMoreSuggestList(
        @Query("size") size: Int,
        @Query("lastPlanId") lastPlanId: Int
    ): ResponseSuggestList
}