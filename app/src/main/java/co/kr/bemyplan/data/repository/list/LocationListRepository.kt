package co.kr.bemyplan.data.repository.list

import co.kr.bemyplan.data.entity.list.ResponseLocationList

interface LocationListRepository {
    suspend fun getLocationList(
        area_id: Int,
        page: Int,
        pageSize: Int,
        sort: String
    ): ResponseLocationList
}