package co.kr.bemyplan.data.entity.purchase.after

import com.google.gson.annotations.SerializedName

data class Post(
    @SerializedName("author_id")
    val authorId: Int,
    val author: String,
    val title: String,
    val spots: List<List<Spot>>,
    val total_days: Int
)
