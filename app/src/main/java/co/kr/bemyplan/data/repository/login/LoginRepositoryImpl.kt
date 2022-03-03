package co.kr.bemyplan.data.repository.login

import co.kr.bemyplan.data.api.LoginService
import co.kr.bemyplan.data.entity.login.check.RequestDuplicatedNickname
import co.kr.bemyplan.data.entity.login.check.ResponseDuplicatedNickname
import co.kr.bemyplan.data.entity.login.login.RequestLogin
import co.kr.bemyplan.data.entity.login.login.ResponseLogin
import co.kr.bemyplan.data.entity.login.signup.RequestSignUp
import co.kr.bemyplan.data.entity.login.signup.ResponseSignUp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val service: LoginService
) : LoginRepository {
    override suspend fun postLogin(requestLogin: RequestLogin): ResponseLogin {
        return withContext(Dispatchers.IO) {
            service.postLogin(requestLogin)
        }
    }

    override suspend fun postDuplicatedNickname(requestDuplicatedNickname: RequestDuplicatedNickname): ResponseDuplicatedNickname {
        return withContext(Dispatchers.IO) {
            service.postDuplicatedNickname(requestDuplicatedNickname)
        }
    }

    override suspend fun postSignUp(requestSignUp: RequestSignUp): ResponseSignUp {
        return withContext(Dispatchers.IO) {
            service.postSignUp(requestSignUp)
        }
    }
}