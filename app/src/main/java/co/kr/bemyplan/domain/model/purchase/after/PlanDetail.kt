package co.kr.bemyplan.domain.model.purchase.after

import co.kr.bemyplan.domain.model.list.ContentModel

data class PlanDetail(
    val contents: List<Contents>,
    val createdAt: String,
    val planId: Int,
    val title: String,
    val updatedAt: String,
    val user: ContentModel.User
)
