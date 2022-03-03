package co.kr.bemyplan.data.entity.scrap

data class ResponseScrap(
    val data: Data
) {
    data class Data(
        val scrapped: Boolean
    )
}
