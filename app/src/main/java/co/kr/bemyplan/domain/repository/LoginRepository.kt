package co.kr.bemyplan.domain.repository

import co.kr.bemyplan.domain.model.login.UserInfoModel

interface LoginRepository {
    suspend fun postLogin(socialType: String, token: String): UserInfoModel
    suspend fun postDuplicatedNickname(nickname: String): String
    suspend fun postSignUp(requestSignUp: RequestSignUp): ResponseSignUp
}