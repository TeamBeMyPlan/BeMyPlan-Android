package co.kr.bemyplan.data.entity.list

import co.kr.bemyplan.domain.model.list.ContentModel

data class ResponseUserPostList(
    val data: Data
) {
    data class Data(
        val items: List<ContentModel>,
        val totalPage: Int
    )
}