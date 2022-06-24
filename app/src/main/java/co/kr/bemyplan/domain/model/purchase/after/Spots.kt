package co.kr.bemyplan.domain.model.purchase.after

data class Spots(
    val createdAt: String,
    var images: List<Images>,
    val latitude: Double,
    val longitude: Double,
    val name: String,
    var review: String,
    var tip: String?,
    val updatedAt: String
)
