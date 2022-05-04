package co.kr.bemyplan.data.repository.list.location

import co.kr.bemyplan.data.api.LocationListService
import co.kr.bemyplan.domain.model.list.PlanList
import co.kr.bemyplan.domain.repository.LocationListRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LocationListRepositoryImpl @Inject constructor(
    private val service: LocationListService,
    private val coroutineDispatcher: CoroutineDispatcher
) : LocationListRepository {
    override suspend fun fetchLocationList(
        region: String,
        size: Int,
        sort: String
    ): PlanList {
        return withContext(coroutineDispatcher) {
            service.fetchLocationList(region, size, sort).data
        }
    }

    override suspend fun fetchMoreLocationList(
        region: String,
        size: Int,
        sort: String,
        lastPlanId: Int
    ): PlanList {
        return withContext(coroutineDispatcher) {
            service.fetchMoreLocationList(region, size, sort, lastPlanId).data
        }
    }
}