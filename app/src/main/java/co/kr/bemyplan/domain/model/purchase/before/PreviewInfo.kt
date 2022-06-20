package co.kr.bemyplan.domain.model.purchase.before

data class PreviewInfo(
    val planId: Int,
    val title: String,
    val description: String,
    val spotCnt: Int,
    val rstrnCnt: Int,
    val price: Int,
    val budget: Budget,
    val month: Int,
    val totalDay: Int,
    var theme: String,
    var partner: String,
    var mobility: String
) {
    data class Budget(
        val amount: Int
    )
}