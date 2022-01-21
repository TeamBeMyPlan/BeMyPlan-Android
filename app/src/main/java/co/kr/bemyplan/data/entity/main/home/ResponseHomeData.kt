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
            @SerializedName("post_id")
            val postId: Int,
            val title: String,
            val author : String,
            @SerializedName("thumbnail_url")
            val thumbnailUrl: String,
            val price: Int,
            @SerializedName("is_purchased")
            val isPurchased : Boolean,
            @SerializedName("is_scraped")
            val isScraped : Boolean
        )
    }
}