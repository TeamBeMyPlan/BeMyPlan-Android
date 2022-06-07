package co.kr.bemyplan.data.entity.purchase.check

data class ResponseCheckPurchased(
    val resultCode: String,
    val message: String,
    val data: String
) {
    fun toModel(): Boolean {
        return when (data) {
            "OK" -> false
            else -> true
        }
    }
}
