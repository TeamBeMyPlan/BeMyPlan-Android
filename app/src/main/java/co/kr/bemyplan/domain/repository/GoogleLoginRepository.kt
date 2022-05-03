package co.kr.bemyplan.domain.repository

interface GoogleLoginRepository {
    suspend fun postGoogleLogin(
        grantType: String,
        clientId: String,
        clientSecret: String,
        redirectUri: String,
        code: String
    ): String
}