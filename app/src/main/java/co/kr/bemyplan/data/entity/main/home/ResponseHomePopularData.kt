package co.kr.bemyplan.data.entity.main.home

import com.google.gson.annotations.SerializedName

data class ResponseHomePopularData(
    val data : List<Data>
){
    data class Data(
        @SerializedName("post_id")
        val postId : Int,
        val title : String,
        val author : String,
        val price : Int,
        @SerializedName("thumbnail_url")
        val thumbnailUrl : String,
        @SerializedName("is_purchased")
        val isPurchased : Boolean
    )
}
