package co.kr.bemyplan.data.entity.purchase.after

data class SpotDetailResponse(
    val contents: List<SpotDetailContentsResponse>,
    val latitude: Double,
    val longitude: Double,
    val name: String,
)
