package co.kr.bemyplan.data.entity.purchase.after

import co.kr.bemyplan.data.entity.login.UserInfoResponse

data class PlanDetailResponse(
    val contents: ScheduleDetailResponse,
    val createdAt: String,
    val planId: Int,
    val title: String,
    val updatedAt: String,
    val user: UserInfoResponse
) {
}
