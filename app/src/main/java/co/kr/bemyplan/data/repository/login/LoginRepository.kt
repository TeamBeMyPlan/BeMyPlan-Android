package co.kr.bemyplan.data.repository.login

import co.kr.bemyplan.data.entity.login.login.RequestLogin
import co.kr.bemyplan.data.entity.login.login.ResponseLogin
import co.kr.bemyplan.data.entity.login.check.RequestDuplicatedNickname
import co.kr.bemyplan.data.entity.login.check.ResponseDuplicatedNickname
import co.kr.bemyplan.data.entity.login.signup.RequestSignUp
import co.kr.bemyplan.data.entity.login.signup.ResponseSignUp

interface LoginRepository {
    suspend fun postLogin(requestLogin: RequestLogin): ResponseLogin
    suspend fun postDuplicatedNickname(requestDuplicatedNickname: RequestDuplicatedNickname): ResponseDuplicatedNickname
    suspend fun postSignUp(requestSignUp: RequestSignUp): ResponseSignUp
}