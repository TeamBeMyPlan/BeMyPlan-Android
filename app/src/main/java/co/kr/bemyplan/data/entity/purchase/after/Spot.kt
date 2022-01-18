package co.kr.bemyplan.data.entity.purchase.after

data class Spot(
    val title: String,
    val description: String,
    val photo_urls: List<String>,
    val address: String,
    val latitude: Double,
    val longitude: Double,
    val next_spot_mobility: String,
    val next_spot_required_time: String
)
