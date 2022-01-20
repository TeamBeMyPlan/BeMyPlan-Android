package co.kr.bemyplan.data.entity.purchase.after

import com.google.gson.annotations.SerializedName

data class Spot(
    val title: String,
    val description: String,
    @SerializedName("photo_urls")
    val photoUrls: List<String>,
    val address: String,
    val latitude: Double,
    val longitude: Double,
    @SerializedName("next_spot_mobility")
    val nextSpotMobility: String,
    @SerializedName("next_spot_required_time")
    val nextSpotRequiredTime: String
)
