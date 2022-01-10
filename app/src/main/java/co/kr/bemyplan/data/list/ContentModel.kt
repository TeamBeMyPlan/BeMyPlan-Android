package co.kr.bemyplan.data.list

data class ContentModel(
    val photo: Int,
    val author: String,
    val title: String,
    var isScrap: Boolean,
    val isFree: Boolean
)