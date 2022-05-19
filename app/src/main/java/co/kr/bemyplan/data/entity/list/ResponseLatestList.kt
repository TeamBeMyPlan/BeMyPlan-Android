package co.kr.bemyplan.data.entity.list

import co.kr.bemyplan.domain.model.list.PlanList

data class ResponseLatestList(
    val resultCode: String,
    val message: String,
    val data: PlanList
)