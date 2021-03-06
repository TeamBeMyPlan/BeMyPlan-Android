package co.kr.bemyplan.di

import co.kr.bemyplan.BuildConfig
import co.kr.bemyplan.data.interceptor.AuthInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {
    @Singleton
    @Provides
    fun provideAuthInterceptor(authInterceptor: AuthInterceptor): Interceptor = authInterceptor

    @Singleton
    @Provides
    fun provideOkHttpInterceptor(
        authInterceptor: AuthInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addNetworkInterceptor(
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            )
            .addInterceptor(authInterceptor)
            .build()
    }

    @BeMyPlanRetrofit
    @Singleton
    @Provides
    fun provideRetrofit(
        client: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @GoogleRetrofit
    @Singleton
    @Provides
    fun provideGoogleLogin(
        client: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://www.googleapis.com")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class BeMyPlanRetrofit

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class GoogleRetrofit