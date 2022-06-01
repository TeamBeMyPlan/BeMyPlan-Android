package co.kr.bemyplan.domain.repository

import co.kr.bemyplan.domain.model.purchase.after.moveInfo.MoveInfo

interface MoveInfoRepository {
    suspend fun fetchMoveInfo(planId: Int): List<MoveInfo>
}