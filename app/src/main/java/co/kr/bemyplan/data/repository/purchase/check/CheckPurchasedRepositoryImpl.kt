package co.kr.bemyplan.data.repository.purchase.check

import co.kr.bemyplan.data.api.CheckPurchasedService
import co.kr.bemyplan.domain.repository.CheckPurchasedRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CheckPurchasedRepositoryImpl @Inject constructor(
    private val service: CheckPurchasedService,
    private val coroutineDispatcher: CoroutineDispatcher
) : CheckPurchasedRepository {
    override suspend fun checkPurchased(planId: Int): Boolean {
        return withContext(coroutineDispatcher) {
            service.checkPurchased(planId).toModel()
        }
    }
}