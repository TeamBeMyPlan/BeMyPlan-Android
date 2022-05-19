package co.kr.bemyplan.di

import co.kr.bemyplan.data.api.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {
    // 구매 전 여행일정 컨텐츠 뷰
    @Singleton
    @Provides
    fun providePreviewService(@BeMyPlanRetrofit retrofit: Retrofit): PreviewService {
        return retrofit.create(PreviewService::class.java)
    }

    // 스크랩 뷰
    @Singleton
    @Provides
    fun provideScrapListService(@BeMyPlanRetrofit retrofit: Retrofit): ScrapListService {
        return retrofit.create(ScrapListService::class.java)
    }

    // 최신 여행 일정 리스트 뷰
    @Singleton
    @Provides
    fun provideLatestListService(@BeMyPlanRetrofit retrofit: Retrofit): LatestListService {
        return retrofit.create(LatestListService::class.java)
    }

    // 비마플 추천 여행 일정 리스트 뷰
    @Singleton
    @Provides
    fun provideSuggestListService(@BeMyPlanRetrofit retrofit: Retrofit): SuggestListService {
        return retrofit.create(SuggestListService::class.java)
    }

    // 여행지 리스트 뷰
    @Singleton
    @Provides
    fun provideLocationListService(@BeMyPlanRetrofit retrofit: Retrofit): LocationListService {
        return retrofit.create(LocationListService::class.java)
    }

    // 유저가 작성한 게시물 리스트 뷰
    @Singleton
    @Provides
    fun provideUserPostListService(@BeMyPlanRetrofit retrofit: Retrofit): UserPlanListService {
        return retrofit.create(UserPlanListService::class.java)
    }

    // 마이페이지 뷰
    @Singleton
    @Provides
    fun provideMyPlanService(@BeMyPlanRetrofit retrofit: Retrofit): MyPlanService {
        return retrofit.create(MyPlanService::class.java)
    }

    // 로그인 뷰
    @Singleton
    @Provides
    fun provideLoginService(@BeMyPlanRetrofit retrofit: Retrofit): LoginService {
        return retrofit.create(LoginService::class.java)
    }

    @Singleton
    @Provides
    fun provideGoogleLoginService(@GoogleRetrofit retrofit: Retrofit): GoogleLoginService {
        return retrofit.create(GoogleLoginService::class.java)
    }

    // 스크랩 버튼 클릭
    @Singleton
    @Provides
    fun provideScrapService(@BeMyPlanRetrofit retrofit: Retrofit): ScrapService {
        return retrofit.create(ScrapService::class.java)
    }

    @Singleton
    @Provides
    fun provideAfterPostService(@BeMyPlanRetrofit retrofit: Retrofit): AfterPostService {
        return retrofit.create(AfterPostService::class.java)
    }

    //여행지뷰
    @Singleton
    @Provides
    fun provideLocationService(@BeMyPlanRetrofit retrofit: Retrofit):LocationService {
        return retrofit.create(LocationService::class.java)
    }

    // 결제
    @Singleton
    @Provides
    fun providePurchaseService(@BeMyPlanRetrofit retrofit: Retrofit): PurchaseService {
        return retrofit.create(PurchaseService::class.java)
    }

    //홈뷰 인기 일정
    @Singleton
    @Provides
    fun provideHomePopularService(@BeMyPlanRetrofit retrofit: Retrofit):HomePopularService{
        return retrofit.create(HomePopularService::class.java)
    }

    //홈뷰 최신 일정
    @Singleton
    @Provides
    fun provideHomeNewService(@BeMyPlanRetrofit retrofit: Retrofit):HomeNewService{
        return retrofit.create(HomeNewService::class.java)
    }

    //홈뷰 추천 일정
    @Singleton
    @Provides
    fun provideHomeSuggestService(@BeMyPlanRetrofit retrofit: Retrofit):HomeSuggestService{
        return retrofit.create(HomeSuggestService::class.java)
    }
}