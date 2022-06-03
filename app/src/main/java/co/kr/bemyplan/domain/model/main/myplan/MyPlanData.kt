package co.kr.bemyplan.domain.model.main.myplan

import co.kr.bemyplan.data.entity.main.myplan.User

data class MyPlanData(
    val contents: List<Data>,
    val nextCursor: Int
){
    data class Data(
        val createdAt: String,
        val orderStatus: Boolean,
        val planId: Int,
        val scrapStatus: Boolean,
        val thumbnailUrl: String,
        val title: String,
        val updatedAt: String,
        val user: User
    )
}
