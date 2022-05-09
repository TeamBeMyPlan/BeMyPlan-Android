package co.kr.bemyplan.data.repository.purchase.after.moveInfo

import co.kr.bemyplan.data.api.MoveInfoService
import co.kr.bemyplan.domain.model.purchase.after.moveInfo.MoveInfo
import co.kr.bemyplan.domain.repository.MoveInfoRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MoveInfoRepositoryImpl @Inject constructor(
    private val service: MoveInfoService,
    private val coroutineDispatcher: CoroutineDispatcher
) : MoveInfoRepository {
    override suspend fun fetchMoveInfo(planId: Int): List<MoveInfo> {
        return withContext(coroutineDispatcher) {
            service.fetchMoveInfo(planId).data
        }
    }
}