package co.kr.bemyplan.domain.repository

import co.kr.bemyplan.domain.model.main.home.HomeDomainData

interface HomeSuggestRepository {
    suspend fun getHomeSuggestData(size: Int, sort: String): List<HomeDomainData>
}