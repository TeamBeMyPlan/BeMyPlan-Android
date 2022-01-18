package co.kr.bemyplan.data.entity.purchase.after

data class Spot(
    val placeName: String,
    val address: String,
    val photo: List<String>,
    val context: String,
    val transportation: String?,
    val placeNext: String?,
    val duration: Int?,
    val isFirst: Boolean,
    var isLast: Boolean
)
