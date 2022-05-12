package co.kr.bemyplan.data.interceptor

import co.kr.bemyplan.data.local.BeMyPlanDataStore
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val localStorage: BeMyPlanDataStore
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val sessionId = localStorage.sessionId
        val visitOption = if (sessionId != "") MEMBERSHIP else GUEST
        val authRequest = when (sessionId) {
            "" -> {
                originalRequest.newBuilder()
                    .addHeader("Visit-Option", visitOption)
                    .build()
            }
            else -> {
                originalRequest.newBuilder()
                    .addHeader("Authorization", sessionId)
                    .addHeader("Visit-Option", visitOption)
                    .build()
            }
        }
        val response = chain.proceed(authRequest)
        return response
    }

    companion object {
        const val MEMBERSHIP = "MEMBERSHIP"
        const val GUEST = "GUEST"
    }
}