package co.kr.bemyplan.data.entity.main.myplan

import co.kr.bemyplan.domain.model.list.PlanList

data class ResponseMyPlan(
    val resultCode: String,
    val message: String,
    val data: PlanList
)