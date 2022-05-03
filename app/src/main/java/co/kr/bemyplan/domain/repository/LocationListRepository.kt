package co.kr.bemyplan.domain.repository

import co.kr.bemyplan.domain.model.list.ContentModel

interface LocationListRepository {
    suspend fun getLocationList(region: String, size: Int, sort: String): List<ContentModel>
}