package co.kr.bemyplan.domain.model.purchase.after

data class PlanDetail(
    val contents: List<Contents>,
    val createdAt: String,
    val planId: Int,
    val title: String,
    val updatedAt: String,
    val user: User
) {
    data class User(
        val userId: Int,
        val nickname: String
    )
}
