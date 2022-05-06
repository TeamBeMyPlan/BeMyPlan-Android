package co.kr.bemyplan.domain.model.purchase.after

data class Spots(
    val createdAt: String,
    val images: List<Images>,
    val latitude: Double,
    val longitude: Double,
    val name: String,
    val review: String,
    val tip: String,
    val updatedAt: String
)
