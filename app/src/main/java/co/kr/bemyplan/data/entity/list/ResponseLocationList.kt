package co.kr.bemyplan.data.entity.list

data class ResponseLocationList(
    val data: Data
) {
    data class Data(
        val items: List<ContentModel>,
        val totalPage: Int
    )
}
