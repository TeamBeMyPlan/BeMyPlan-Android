package co.kr.bemyplan.domain.model.list

data class PlanList(
    val contents: List<ContentModel>,
    val nextCursor: Int
)
