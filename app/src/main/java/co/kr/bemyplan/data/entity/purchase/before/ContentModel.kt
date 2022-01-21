package co.kr.bemyplan.data.entity.purchase.before

import com.google.gson.annotations.SerializedName

data class ContentModel(
    @SerializedName("photo_urls")
    val photoUrls: List<String>,
    val description: String
)