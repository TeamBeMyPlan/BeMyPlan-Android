package co.kr.bemyplan.domain.repository

import co.kr.bemyplan.domain.model.main.home.HomeDomainData

interface HomeNewRepository {
    suspend fun getHomeNewData(size: Int, sort: String): List<HomeDomainData>
}