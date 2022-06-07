package co.kr.bemyplan.domain.repository

interface CheckPurchasedRepository {
    suspend fun checkPurchased(planId: Int): Boolean
}