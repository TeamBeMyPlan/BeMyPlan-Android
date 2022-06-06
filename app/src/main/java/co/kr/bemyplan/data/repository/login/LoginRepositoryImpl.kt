package co.kr.bemyplan.data.repository.login

import co.kr.bemyplan.data.api.LoginService
import co.kr.bemyplan.data.entity.login.login.RequestLogin
import co.kr.bemyplan.data.entity.login.signup.RequestSignUp
import co.kr.bemyplan.domain.model.login.UserInfoModel
import co.kr.bemyplan.domain.repository.LoginRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val service: LoginService,
    private val coroutineDispatcher: CoroutineDispatcher
) : LoginRepository {
    override suspend fun postLogin(socialType: String, token: String): UserInfoModel {
        return withContext(coroutineDispatcher) {
            service.postLogin(RequestLogin(token, socialType)).data.toModel()
        }
    }

    override suspend fun postDuplicatedNickname(nickname: String): String {
        return withContext(coroutineDispatcher) {
            service.postDuplicatedNickname(nickname).data
        }
    }

    override suspend fun postSignUp(
        token: String,
        socialType: String,
        nickname: String,
        email: String
    ): UserInfoModel {
        return withContext(coroutineDispatcher) {
            service.postSignUp(RequestSignUp(token, socialType, nickname, email)).data.toModel()
        }
    }
}