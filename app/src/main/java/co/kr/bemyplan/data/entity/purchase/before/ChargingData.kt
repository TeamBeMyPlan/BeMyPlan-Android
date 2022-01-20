package co.kr.bemyplan.data.entity.purchase.before

data class ChargingData(
    val selected : Boolean,
    val payWay : Int,
    val writter : String,
    val title : String,
    val img : String,
    val price : Int
)
