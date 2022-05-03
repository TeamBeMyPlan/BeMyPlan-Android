package co.kr.bemyplan.data.repository.login

import co.kr.bemyplan.data.api.LoginService
import co.kr.bemyplan.data.entity.login.login.RequestLogin
import co.kr.bemyplan.data.entity.login.login.ResponseLogin
import co.kr.bemyplan.data.entity.login.signup.RequestSignUp
import co.kr.bemyplan.data.entity.login.signup.ResponseSignUp
import co.kr.bemyplan.di.IoDispatcher
import co.kr.bemyplan.domain.repository.LoginRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val service: LoginService,
    private val coroutineDispatcher: CoroutineDispatcher
) : LoginRepository {
    override suspend fun postLogin(requestLogin: RequestLogin): ResponseLogin {
        return withContext(coroutineDispatcher) {
            service.postLogin(requestLogin)
        }
    }

    override suspend fun postDuplicatedNickname(nickname: String): String {
        return withContext(coroutineDispatcher) {
            service.postDuplicatedNickname(nickname).data
        }
    }

    override suspend fun postSignUp(requestSignUp: RequestSignUp): ResponseSignUp {
        return withContext(coroutineDispatcher) {
            service.postSignUp(requestSignUp)
        }
    }
}