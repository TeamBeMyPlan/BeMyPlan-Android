package co.kr.bemyplan.di

import co.kr.bemyplan.BuildConfig
import co.kr.bemyplan.data.api.PreviewInfoService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RetrofitModule {
    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun providePreviewInfoService(retrofit: Retrofit): PreviewInfoService {
        return retrofit.create(PreviewInfoService::class.java)
    }
}