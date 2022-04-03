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
        val authRequest =
            originalRequest.newBuilder()
                .addHeader("Authorization", localStorage.sessionId)
                .build()
        val response = chain.proceed(authRequest)
        return response
    }
}