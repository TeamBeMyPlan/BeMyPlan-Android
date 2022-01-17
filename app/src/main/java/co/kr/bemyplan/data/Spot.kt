package co.kr.bemyplan.data

data class Spot(
    val placeName: String,
    val address: String,
    val photo: String,
    val context: String,
    val transportation: String?,
    val placeNext: String?,
    val duration: Int?,
    val isFirst: Boolean,
    var isLast: Boolean
)
