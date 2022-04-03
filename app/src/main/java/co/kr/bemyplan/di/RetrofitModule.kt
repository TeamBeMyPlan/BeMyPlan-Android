package co.kr.bemyplan.di

import co.kr.bemyplan.BuildConfig
import co.kr.bemyplan.data.api.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {
    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient.Builder()
            .addNetworkInterceptor(interceptor)
            .build()

        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    // 구매 전 여행일정 컨텐츠 뷰
    @Singleton
    @Provides
    fun providePreviewService(retrofit: Retrofit): PreviewService {
        return retrofit.create(PreviewService::class.java)
    }

    // 스크랩 뷰
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

    // 비마플 추천 여행 일정 리스트 뷰
    @Singleton
    @Provides
    fun provideSuggestListService(retrofit: Retrofit): SuggestListService {
        return retrofit.create(SuggestListService::class.java)
    }

    // 여행지 리스트 뷰
    @Singleton
    @Provides
    fun provideLocationListService(retrofit: Retrofit): LocationListService {
        return retrofit.create(LocationListService::class.java)
    }

    // 유저가 작성한 게시물 리스트 뷰
    @Singleton
    @Provides
    fun provideUserPostListService(retrofit: Retrofit): UserPostListService {
        return retrofit.create(UserPostListService::class.java)
    }

    // 마이페이지 뷰
    @Singleton
    @Provides
    fun provideMyPlanService(retrofit: Retrofit): MyPlanService {
        return retrofit.create(MyPlanService::class.java)
    }

    // 로그인 뷰
    @Singleton
    @Provides
    fun provideLoginService(retrofit: Retrofit): LoginService {
        return retrofit.create(LoginService::class.java)
    }

    // 스크랩 버튼 클릭
    @Singleton
    @Provides
    fun providePostScrapService(retrofit: Retrofit): PostScrapService {
        return retrofit.create(PostScrapService::class.java)
    }

    @Singleton
    @Provides
    fun afterPostService(retrofit: Retrofit): AfterPostService {
        return retrofit.create(AfterPostService::class.java)
    }
}