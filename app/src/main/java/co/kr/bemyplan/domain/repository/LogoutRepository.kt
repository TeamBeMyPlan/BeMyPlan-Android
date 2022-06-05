package co.kr.bemyplan.domain.repository

interface LogoutRepository {
    suspend fun logout()
}