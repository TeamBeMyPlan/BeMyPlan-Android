package co.kr.bemyplan.data.entity.list

data class ResponseLatestList(
    val data: Data
) {
    data class Data(
        val items: List<ContentModel>,
        val totalPage: Int
    )
}