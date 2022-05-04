package co.kr.bemyplan.data.entity.purchase.before

import co.kr.bemyplan.domain.model.purchase.before.PreviewPlan

data class ResponsePreviewPlan(
    val resultCode: String,
    val message: String,
    val data: PreviewPlan
)
