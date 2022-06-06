package co.kr.bemyplan.data.entity.scrap

data class ResponseScrap(
    val resultCode: String,
    val message: String,
    val data: String
) {
    fun toModel(): Boolean {
        return when (data) {
            "OK" -> true
            else -> false
        }
    }
}