package co.kr.bemyplan.domain.model.purchase.after

import co.kr.bemyplan.domain.model.purchase.after.moveInfo.Infos

data class MergedPlanAndInfo(
    val day: Int,
    val infos: List<Pair<Infos?, Spots>>
)
