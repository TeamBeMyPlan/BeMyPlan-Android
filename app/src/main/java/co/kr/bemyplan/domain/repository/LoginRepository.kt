package co.kr.bemyplan.domain.repository

import co.kr.bemyplan.data.entity.login.login.RequestLogin
import co.kr.bemyplan.data.entity.login.login.ResponseLogin
import co.kr.bemyplan.data.entity.login.signup.RequestSignUp
import co.kr.bemyplan.data.entity.login.signup.ResponseSignUp

interface LoginRepository {
    suspend fun postLogin(requestLogin: RequestLogin): ResponseLogin
    suspend fun postDuplicatedNickname(nickname: String): String
    suspend fun postSignUp(requestSignUp: RequestSignUp): ResponseSignUp
}