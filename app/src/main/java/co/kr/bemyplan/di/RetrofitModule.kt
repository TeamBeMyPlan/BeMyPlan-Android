package co.kr.bemyplan.di

import co.kr.bemyplan.BuildConfig
import co.kr.bemyplan.data.api.PreviewService
import co.kr.bemyplan.data.api.ScrapListService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
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
    fun providePreviewService(retrofit: Retrofit): PreviewService {
        return retrofit.create(PreviewService::class.java)
    }

    @Singleton
    @Provides
    fun provideScrapListService(retrofit: Retrofit): ScrapListService {
        return retrofit.create(ScrapListService::class.java)
    }
}