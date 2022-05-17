package co.kr.bemyplan.domain.repository

import co.kr.bemyplan.data.entity.main.myplan.logout.ResponseLogout

interface LogoutRepository {
    suspend fun postLogout(): ResponseLogout
}