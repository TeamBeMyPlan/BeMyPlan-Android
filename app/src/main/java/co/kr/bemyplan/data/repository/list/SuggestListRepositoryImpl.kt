package co.kr.bemyplan.data.repository.list

import co.kr.bemyplan.data.api.SuggestListService
import co.kr.bemyplan.domain.model.list.PlanList
import co.kr.bemyplan.domain.repository.SuggestListRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SuggestListRepositoryImpl @Inject constructor(
    private val service: SuggestListService,
    private val coroutineDispatcher: CoroutineDispatcher
) : SuggestListRepository {
    override suspend fun fetchSuggestList(size: Int): PlanList {
        return withContext(coroutineDispatcher) {
            service.fetchSuggestList(size).data
        }
    }

    override suspend fun fetchMoreSuggestList(size: Int, lastPlanId: Int): PlanList {
        return withContext(coroutineDispatcher) {
            service.fetchMoreSuggestList(size, lastPlanId).data
        }
    }
}