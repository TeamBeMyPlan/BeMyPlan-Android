package co.kr.bemyplan.data.repository.purchase.preview

import co.kr.bemyplan.data.api.PurchaseService
import co.kr.bemyplan.data.entity.purchase.before.RequestPurchase
import co.kr.bemyplan.domain.repository.PurchaseRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PurchaseRepositoryImpl @Inject constructor(
    private val service: PurchaseService,
    private val coroutineDispatcher: CoroutineDispatcher
) : PurchaseRepository {
    override suspend fun purchase(planId: Int): Result<String> {
        return withContext(coroutineDispatcher) {
            kotlin.runCatching {
                service.purchase(RequestPurchase(planId)).data
            }
        }
    }
}