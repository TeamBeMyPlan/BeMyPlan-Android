package co.kr.bemyplan.di

import co.kr.bemyplan.BuildConfig
import co.kr.bemyplan.data.api.LatestListService
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

    // 구매 전 여행일정 컨텐츠 뷰
    @Singleton
    @Provides
    fun providePreviewService(retrofit: Retrofit): PreviewService {
        return retrofit.create(PreviewService::class.java)
    }

    // 스크랩
    @Singleton
    @Provides
    fun provideScrapListService(retrofit: Retrofit): ScrapListService {
        return retrofit.create(ScrapListService::class.java)
    }

    // 최신 여행 일정 리스트 뷰
    @Singleton
    @Provides
    fun provideLatestListService(retrofit: Retrofit): LatestListService {
        return retrofit.create(LatestListService::class.java)
    }
}