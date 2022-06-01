package co.kr.bemyplan.di

import co.kr.bemyplan.data.api.*
import co.kr.bemyplan.data.repository.list.LatestListRepositoryImpl
import co.kr.bemyplan.data.repository.list.LocationListRepositoryImpl
import co.kr.bemyplan.data.repository.list.SuggestListRepositoryImpl
import co.kr.bemyplan.data.repository.list.UserPostListRepositoryImpl
import co.kr.bemyplan.data.repository.login.GoogleLoginRepositoryImpl
import co.kr.bemyplan.data.repository.login.LoginRepositoryImpl
import co.kr.bemyplan.data.repository.main.home.HomeNewRepositoryImpl
import co.kr.bemyplan.data.repository.main.home.HomePopularRepositoryImpl
import co.kr.bemyplan.data.repository.main.home.HomeSuggestRepositoryImpl
import co.kr.bemyplan.data.repository.main.location.LocationRepositoryImpl
import co.kr.bemyplan.data.repository.main.myplan.MyPlanRepository
import co.kr.bemyplan.data.repository.main.myplan.MyPlanRepositoryImpl
import co.kr.bemyplan.data.repository.purchase.after.PlanDetailRepositoryImpl
import co.kr.bemyplan.data.repository.purchase.after.moveInfo.MoveInfoRepositoryImpl
import co.kr.bemyplan.domain.repository.ScrapListRepository
import co.kr.bemyplan.data.repository.main.scrap.ScrapListRepositoryImpl
import co.kr.bemyplan.data.repository.purchase.preview.PreviewRepositoryImpl
import co.kr.bemyplan.data.repository.purchase.preview.PurchaseRepositoryImpl
import co.kr.bemyplan.domain.repository.ScrapRepository
import co.kr.bemyplan.data.repository.scrap.ScrapRepositoryImpl
import co.kr.bemyplan.domain.repository.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.CoroutineDispatcher

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {
    // 구매 전 여행일정 컨텐츠 뷰
    @ViewModelScoped
    @Provides
    fun providePreviewRepository(
        previewService: PreviewService,
        @IoDispatcher coroutineDispatcher: CoroutineDispatcher
    ): PreviewRepository {
        return PreviewRepositoryImpl(previewService, coroutineDispatcher)
    }

    // 스크랩 뷰
    @ViewModelScoped
    @Provides
    fun provideScrapListRepository(
        scrapListService: ScrapListService,
        @IoDispatcher coroutineDispatcher: CoroutineDispatcher
    ): ScrapListRepository {
        return ScrapListRepositoryImpl(scrapListService, coroutineDispatcher)
    }

    // 최신 여행 일정 리스트 뷰
    @ViewModelScoped
    @Provides
    fun provideLatestListRepository(
        latestListService: LatestListService,
        @IoDispatcher coroutineDispatcher: CoroutineDispatcher
    ): LatestListRepository {
        return LatestListRepositoryImpl(latestListService, coroutineDispatcher)
    }

    // 비마플 추천 여행 일정 리스트 뷰
    @ViewModelScoped
    @Provides
    fun provideSuggestListRepository(
        suggestListService: SuggestListService,
        @IoDispatcher coroutineDispatcher: CoroutineDispatcher
    ): SuggestListRepository {
        return SuggestListRepositoryImpl(suggestListService, coroutineDispatcher)
    }

    // 여행지 리스트 뷰
    @ViewModelScoped
    @Provides
    fun provideLocationListRepository(
        locationListService: LocationListService,
        @IoDispatcher coroutineDispatcher: CoroutineDispatcher
    ): LocationListRepository {
        return LocationListRepositoryImpl(locationListService, coroutineDispatcher)
    }

    // 유저가 작성한 게시물 리스트 뷰
    @ViewModelScoped
    @Provides
    fun provideUserPostListRepository(
        userPlanListService: UserPlanListService,
        @IoDispatcher coroutineDispatcher: CoroutineDispatcher
    ): UserPostListRepository {
        return UserPostListRepositoryImpl(userPlanListService, coroutineDispatcher)
    }

    // 마이페이지 뷰
    @ViewModelScoped
    @Provides
    fun provideMyPlanRepository(
        myPlanService: MyPlanService
    ): MyPlanRepository {
        return MyPlanRepositoryImpl(myPlanService)
    }

    // 로그인 뷰
    @ViewModelScoped
    @Provides
    fun provideLoginRepository(
        loginService: LoginService,
        @IoDispatcher coroutineDispatcher: CoroutineDispatcher
    ): LoginRepository {
        return LoginRepositoryImpl(loginService, coroutineDispatcher)
    }

    // 구글 로그인
    @ViewModelScoped
    @Provides
    fun provideGoogleSignInRepository(
        googleLoginService: GoogleLoginService,
        @IoDispatcher coroutineDispatcher: CoroutineDispatcher
    ): GoogleLoginRepository {
        return GoogleLoginRepositoryImpl(googleLoginService, coroutineDispatcher)
    }

    // 스크랩 버튼 클릭
    @ViewModelScoped
    @Provides
    fun provideScrapRepository(
        scrapService: ScrapService,
        @IoDispatcher coroutineDispatcher: CoroutineDispatcher
    ): ScrapRepository {
        return ScrapRepositoryImpl(scrapService, coroutineDispatcher)
    }

    //여행지뷰
    @ViewModelScoped
    @Provides
    fun provideLocationRepository(
        locationService: LocationService,
        @IoDispatcher coroutineDispatcher: CoroutineDispatcher
    ): LocationRepository {
        return LocationRepositoryImpl(locationService, coroutineDispatcher)
    }

    // 결제
    @ViewModelScoped
    @Provides
    fun providePurchaseRepository(
        purchaseService: PurchaseService,
        @IoDispatcher coroutineDispatcher: CoroutineDispatcher
    ): PurchaseRepository = PurchaseRepositoryImpl(purchaseService, coroutineDispatcher)

    //홈뷰 인기 일정
    @ViewModelScoped
    @Provides
    fun provideHomePopularRepository(
        homePopularService: HomePopularService,
        @IoDispatcher coroutineDispatcher: CoroutineDispatcher
    ) : HomePopularRepository{
        return HomePopularRepositoryImpl(homePopularService, coroutineDispatcher)
    }

    //홈뷰 최신 일정
    @ViewModelScoped
    @Provides
    fun provideHomeNewRepository(
        homeNewService: HomeNewService,
        @IoDispatcher coroutineDispatcher: CoroutineDispatcher
    ) : HomeNewRepository{
        return HomeNewRepositoryImpl(homeNewService, coroutineDispatcher)
    }

    //홈뷰 추천 일정
    @ViewModelScoped
    @Provides
    fun provideHomeSuggestRepository(
        homeSuggestService: HomeSuggestService,
        @IoDispatcher coroutineDispatcher: CoroutineDispatcher
    ) : HomeSuggestRepository{
        return HomeSuggestRepositoryImpl(homeSuggestService, coroutineDispatcher)
    }

    // 상세한 일정
    @ViewModelScoped
    @Provides
    fun providePlanDetailRepository(
        planDetailService: PlanDetailService,
        @IoDispatcher coroutineDispatcher: CoroutineDispatcher
    ): PlanDetailRepository {
        return PlanDetailRepositoryImpl(planDetailService, coroutineDispatcher)
    }

    // moveInfo 정보
    @ViewModelScoped
    @Provides
    fun provideMoveInfoRepository(
        moveInfoService: MoveInfoService,
        @IoDispatcher coroutineDispatcher: CoroutineDispatcher
    ): MoveInfoRepository {
        return MoveInfoRepositoryImpl(moveInfoService, coroutineDispatcher)
    }
}