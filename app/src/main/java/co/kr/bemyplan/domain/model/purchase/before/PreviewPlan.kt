package co.kr.bemyplan.domain.model.purchase.before

data class PreviewPlan(
    val createdAt: String,
    val updatedAt: String,
    val previewInfo: PreviewInfo,
    val previewContents: List<PreviewContents>
)
