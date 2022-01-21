package co.kr.bemyplan.data.entity.purchase.before

import com.google.gson.annotations.SerializedName

data class PreviewInfoModel(
    @SerializedName("author_id")
    val authorId: Int,
    val author: String,
    val title: String,
    val price: Int,
    val description: String,
    @SerializedName("tag_theme")
    val tagTheme: String,
    @SerializedName("tag_count_spot")
    val tagCountSpot: Int,
    @SerializedName("tag_count_day")
    val tagCountDay: Int,
    @SerializedName("tag_count_restaurant")
    val tagCountRestaurant: Int,
    @SerializedName("tag_partner")
    val tagPartner: String,
    @SerializedName("tag_money")
    val tagMoney: String,
    @SerializedName("tag_mobility")
    val tagMobility: String,
    @SerializedName("tag_month")
    val tagMonth: Int
)