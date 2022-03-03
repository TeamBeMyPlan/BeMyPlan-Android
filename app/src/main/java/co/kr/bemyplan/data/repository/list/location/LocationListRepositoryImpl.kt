package co.kr.bemyplan.data.repository.list.location

import co.kr.bemyplan.data.api.ApiService
import co.kr.bemyplan.data.api.LocationListService
import co.kr.bemyplan.data.entity.list.ResponseLocationList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LocationListRepositoryImpl @Inject constructor(
    private val service: LocationListService
) : LocationListRepository {
    override suspend fun getLocationList(
        area_id: Int,
        page: Int,
        pageSize: Int,
        sort: String
    ): ResponseLocationList {
        return withContext(Dispatchers.IO) {
            service.getLocationList(
                area_id,
                page,
                pageSize,
                sort
            )
        }
    }
}