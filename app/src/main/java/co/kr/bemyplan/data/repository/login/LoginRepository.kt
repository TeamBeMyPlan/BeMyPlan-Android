package co.kr.bemyplan.data.repository.login

import co.kr.bemyplan.data.entity.login.RequestLogin
import co.kr.bemyplan.data.entity.login.ResponseLogin
import co.kr.bemyplan.data.entity.login.check.RequestDuplicatedNickname
import co.kr.bemyplan.data.entity.login.check.ResponseDuplicatedNickname

interface LoginRepository {
    suspend fun postLogin(requestLogin: RequestLogin): ResponseLogin
    suspend fun postDuplicatedNickname(requestDuplicatedNickname: RequestDuplicatedNickname): ResponseDuplicatedNickname
}