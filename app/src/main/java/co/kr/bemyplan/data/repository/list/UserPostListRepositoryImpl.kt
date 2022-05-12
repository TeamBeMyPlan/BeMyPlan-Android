package co.kr.bemyplan.data.repository.list

import co.kr.bemyplan.data.api.UserPlanListService
import co.kr.bemyplan.data.entity.list.ResponseUserPlanList
import co.kr.bemyplan.domain.model.list.PlanList
import co.kr.bemyplan.domain.repository.UserPostListRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserPostListRepositoryImpl @Inject constructor(
    private val service: UserPlanListService,
    private val coroutineDispatcher: CoroutineDispatcher
) : UserPostListRepository {
    override suspend fun fetchUserPlanList(size: Int, sort: String, userId: Int): PlanList {
        return withContext(coroutineDispatcher) {
            service.fetchUserPlanList(size, sort, userId).data
        }
    }

    override suspend fun fetchMoreUserPlanList(
        size: Int,
        sort: String,
        userId: Int,
        lastPlanId: Int
    ): PlanList {
        return withContext(coroutineDispatcher) {
            service.fetchMoreUserPlanList(size, sort, userId, lastPlanId).data
        }
    }
}