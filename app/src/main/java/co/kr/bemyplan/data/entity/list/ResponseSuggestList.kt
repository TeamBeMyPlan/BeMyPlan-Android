package co.kr.bemyplan.data.entity.list

import co.kr.bemyplan.domain.model.list.ContentModel
import co.kr.bemyplan.domain.model.list.PlanList

data class ResponseSuggestList(
    val resultCode: String,
    val message: String,
    val data: PlanList
)