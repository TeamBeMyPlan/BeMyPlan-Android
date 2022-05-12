package co.kr.bemyplan.domain.model.list

data class ContentModel(
    val createdAt: String,
    val updatedAt: String,
    val planId: Int,
    val thumbnailUrl: String,
    val title: String,
    val user: User,
    val orderStatus: Boolean,
    var scrapStatus: Boolean,
    val price: Int?,
) {
    data class User(
        val userId: Int,
        val nickname: String
    )
}