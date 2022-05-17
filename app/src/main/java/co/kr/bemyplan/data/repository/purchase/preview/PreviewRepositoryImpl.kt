package co.kr.bemyplan.data.repository.purchase.preview

import co.kr.bemyplan.data.api.PreviewService
import co.kr.bemyplan.domain.model.purchase.before.PreviewPlan
import co.kr.bemyplan.domain.repository.PreviewRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PreviewRepositoryImpl @Inject constructor(
    private val service: PreviewService,
    private val coroutineDispatcher: CoroutineDispatcher
) : PreviewRepository {
    override suspend fun fetchPreviewPlan(planId: Int): PreviewPlan {
        return withContext(coroutineDispatcher) {
            service.fetchPreviewPlan(planId).data.translate()
        }
    }
}