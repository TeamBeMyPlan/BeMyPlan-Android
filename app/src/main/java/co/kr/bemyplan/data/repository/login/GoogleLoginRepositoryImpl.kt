package co.kr.bemyplan.data.repository.login

import co.kr.bemyplan.data.api.GoogleLoginService
import co.kr.bemyplan.data.entity.login.login.RequestGoogleLogin
import co.kr.bemyplan.di.IoDispatcher
import co.kr.bemyplan.domain.repository.GoogleLoginRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GoogleLoginRepositoryImpl @Inject constructor(
    private val service: GoogleLoginService,
    @IoDispatcher private val coroutineDispatcher: CoroutineDispatcher
) : GoogleLoginRepository {
    override suspend fun postGoogleLogin(
        grantType: String,
        clientId: String,
        clientSecret: String,
        redirectUri: String,
        code: String
    ): String {
        return withContext(coroutineDispatcher) {
            service.postGoogleSignInData(
                RequestGoogleLogin(grantType, clientId, clientSecret, redirectUri, code)
            ).accessToken
        }
    }
}