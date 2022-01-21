package co.kr.bemyplan.data.entity.main.scrap

data class ResponseScrap(
    val data: Data
) {
    data class Data(
        val scrapped: Boolean
    )
}
