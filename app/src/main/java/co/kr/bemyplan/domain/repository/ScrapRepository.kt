package co.kr.bemyplan.domain.repository

interface ScrapRepository {
    suspend fun postScrap(planId: Int): Boolean
    suspend fun deleteScrap(planId: Int): Boolean
    suspend fun checkScrapStatus(planId: Int): Boolean
}