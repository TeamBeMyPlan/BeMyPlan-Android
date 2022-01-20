package co.kr.bemyplan.data.entity.main.scrap

import co.kr.bemyplan.data.entity.list.ContentModel

data class ResponseScrapList(
    val data: Data
) {
    data class Data(
        val items: List<ContentModel>,
        val totalPage: Int
    )
}