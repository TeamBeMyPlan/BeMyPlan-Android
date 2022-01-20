package co.kr.bemyplan.data.entity.main.home

import com.google.gson.annotations.SerializedName

data class ResponseHomeData(
    val data: ResponseHomeItems,
    val totalPage : Int
){
    data class ResponseHomeItems(
        val items: List<HomeData>
    ){
        data class HomeData(
            val id: Int,
            @SerializedName("thumbnail_url")
            val thumbnailUrl: String,
            val title: String,
            val price: Int,
            val nickname: String
        )
    }
}