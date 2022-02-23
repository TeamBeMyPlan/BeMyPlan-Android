package co.kr.bemyplan.data.entity.list

import com.google.gson.annotations.SerializedName

data class ContentModel(
    @SerializedName("post_id")
    val postId: Int,
    val title: String,
    @SerializedName("thumbnail_url")
    val thumbnailUrl: String,
    val author: String,
    @SerializedName("is_purchased")
    val isPurchased: Boolean,
    @SerializedName("is_scraped")
    var isScraped: Boolean,
    val price: Int?,
)