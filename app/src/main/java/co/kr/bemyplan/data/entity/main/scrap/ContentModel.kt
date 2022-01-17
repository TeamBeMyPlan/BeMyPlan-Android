package co.kr.bemyplan.data.entity.main.scrap

data class ContentModel(
    val photo: Int,
    val author: String?,
    val title: String,
    var isScrap: Boolean,
    val isFree: Boolean
)
