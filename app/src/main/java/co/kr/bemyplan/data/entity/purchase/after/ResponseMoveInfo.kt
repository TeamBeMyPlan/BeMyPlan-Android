package co.kr.bemyplan.data.entity.purchase.after

import co.kr.bemyplan.domain.model.purchase.after.moveInfo.MoveInfo

data class ResponseMoveInfo(
    val resultCode: String,
    val message: String,
    val data: MoveInfo
)