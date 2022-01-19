package co.kr.bemyplan.data.repository.list.location

import co.kr.bemyplan.data.api.ApiService
import co.kr.bemyplan.data.entity.list.ResponseLocationList

class LocationListRepositoryImpl: LocationListRepository {
    override suspend fun getLocationList(
        area_id: Int,
        page: Int,
        pageSize: Int,
        sort: String
    ): ResponseLocationList {
        return ApiService.locationListService.getLocationList(area_id, page, pageSize, sort)
    }
}