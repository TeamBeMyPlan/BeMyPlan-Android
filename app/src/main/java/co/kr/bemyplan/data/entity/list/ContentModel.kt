package co.kr.bemyplan.data.entity.list

data class ContentModel(
    val id: Int,
    val thumbnail_url: String,
    val title: String,
    val author: String,
    var isScrap: Boolean,
    val price: Int?,
)