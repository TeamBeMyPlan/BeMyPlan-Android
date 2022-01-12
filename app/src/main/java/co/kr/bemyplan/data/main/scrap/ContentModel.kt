package co.kr.bemyplan.data.main.scrap

data class ContentModel(
    val photo: Int,
    val author: String?,
    val title: String,
    var isScrap: Boolean,
    val isFree: Boolean
)
