package co.kr.bemyplan.data.entity.purchase.after

import com.google.gson.annotations.SerializedName

data class Post(
    val author: String,
    val title: String,
    val spots: List<List<Spot>>,
    @SerializedName("total_days")
    val totalDays: Int
) {
}
