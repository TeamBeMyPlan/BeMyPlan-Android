package co.kr.bemyplan.data.entity.list

data class ResponseSuggestList(
    val status: Int,
    val message: String,
    val data: Data
) {
    data class Data(
        val items: List<ContentModel>,
        val totalPage: Int
    )
}
