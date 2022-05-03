package co.kr.bemyplan.data.entity.list

import co.kr.bemyplan.domain.model.list.ContentModel

data class ResponseLocationList(
    val resultCode: String,
    val message: String,
    val data: Data
) {
    data class Data(
        val contents: List<ContentModel>,
        val nextCursor: Int
    )
}
