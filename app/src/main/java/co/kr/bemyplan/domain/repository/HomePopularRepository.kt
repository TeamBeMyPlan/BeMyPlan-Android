package co.kr.bemyplan.domain.repository

import co.kr.bemyplan.domain.model.main.home.HomeDomainData

interface HomePopularRepository {
    suspend fun getHomePopularData(size: Int, sort: String): List<HomeDomainData>
}