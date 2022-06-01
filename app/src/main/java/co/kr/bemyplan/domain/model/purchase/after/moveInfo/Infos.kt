package co.kr.bemyplan.domain.model.purchase.after.moveInfo

data class Infos(
    val fromSpotId: Int,
    val mobility: String,
    val spentMinute: Int,
    val toSpotId: Int
)
