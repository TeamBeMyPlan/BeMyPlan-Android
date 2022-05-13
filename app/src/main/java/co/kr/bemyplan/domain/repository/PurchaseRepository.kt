package co.kr.bemyplan.domain.repository

interface PurchaseRepository {
    suspend fun purchase(planId: Int): Result<String>
}