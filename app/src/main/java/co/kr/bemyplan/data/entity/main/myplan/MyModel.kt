package co.kr.bemyplan.data.entity.main.myplan

import com.google.gson.annotations.SerializedName

data class MyModel(
    @SerializedName("id")
    val postId: Int,
    @SerializedName("thumbnail_url")
    val thumbnailUrl: String,
    val title: String,
    val author: String,
    var isScrapped: Boolean?
)
