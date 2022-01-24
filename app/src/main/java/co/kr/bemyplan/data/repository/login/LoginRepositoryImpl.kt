package co.kr.bemyplan.data.repository.login

import co.kr.bemyplan.data.api.ApiService
import co.kr.bemyplan.data.entity.login.RequestLogin
import co.kr.bemyplan.data.entity.login.ResponseLogin
import co.kr.bemyplan.data.entity.login.check.RequestDuplicatedNickname
import co.kr.bemyplan.data.entity.login.check.ResponseDuplicatedNickname
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LoginRepositoryImpl : LoginRepository {
    override suspend fun postLogin(requestLogin: RequestLogin): ResponseLogin {
        return withContext(Dispatchers.IO) {
            ApiService.loginService.postLogin(requestLogin)
        }
    }

    override suspend fun postDuplicatedNickname(requestDuplicatedNickname: RequestDuplicatedNickname): ResponseDuplicatedNickname {
        return withContext(Dispatchers.IO) {
            ApiService.loginService.postDuplicatedNickname(requestDuplicatedNickname)
        }
    }
}