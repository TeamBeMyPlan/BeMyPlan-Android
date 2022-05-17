package co.kr.bemyplan.domain.model.purchase.before

data class PreviewPlan(
    val createdAt: String,
    val updatedAt: String,
    val previewInfo: PreviewInfo,
    val previewContents: List<PreviewContents>
) {
    fun translate(): PreviewPlan {
        return this.apply {
            previewContents.map {
                if (it.type == "IMAGE") {
                    it.value = it.value.replace(" ", "")
                }
            }
        }
    }
}
