package co.kr.bemyplan.data.entity.main.scrap

import co.kr.bemyplan.domain.model.list.PlanList

data class ResponseScrapList(
    val resultCode: String,
    val message: String,
    val data: PlanList
)