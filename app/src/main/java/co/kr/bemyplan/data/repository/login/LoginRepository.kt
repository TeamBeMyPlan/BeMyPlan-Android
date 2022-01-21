package co.kr.bemyplan.data.repository.login

import co.kr.bemyplan.data.entity.login.RequestLogin
import co.kr.bemyplan.data.entity.login.ResponseLogin

interface LoginRepository {
    suspend fun postLogin(requestLogin: RequestLogin): ResponseLogin
}