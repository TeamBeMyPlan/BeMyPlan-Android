package co.kr.bemyplan.data.repository.login

import android.util.Log
import co.kr.bemyplan.data.api.ApiService
import co.kr.bemyplan.data.entity.login.RequestLogin
import co.kr.bemyplan.data.entity.login.ResponseLogin
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LoginRepositoryImpl : LoginRepository {
    override suspend fun postLogin(requestLogin: RequestLogin): ResponseLogin {
        return withContext(Dispatchers.IO) {
            ApiService.loginService.postLogin(requestLogin)
        }
    }
}