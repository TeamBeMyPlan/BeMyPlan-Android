package co.kr.bemyplan.data.entity.purchase.after

import co.kr.bemyplan.domain.model.purchase.after.PlanDetail

data class ResponsePlanDetail(
    val resultCode: String,
    val message: String,
    val data: PlanDetail
)
