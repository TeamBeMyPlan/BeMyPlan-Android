package co.kr.bemyplan.di

import co.kr.bemyplan.data.api.*
import co.kr.bemyplan.data.repository.list.LatestListRepositoryImpl
import co.kr.bemyplan.data.repository.list.LocationListRepositoryImpl
import co.kr.bemyplan.data.repository.list.SuggestListRepositoryImpl
import co.kr.bemyplan.data.repository.list.UserPostListRepositoryImpl
import co.kr.bemyplan.data.repository.login.GoogleLoginRepositoryImpl
import co.kr.bemyplan.data.repository.login.LoginRepositoryImpl
import co.kr.bemyplan.data.repository.main.location.LocationRepositoryImpl
import co.kr.bemyplan.data.repository.main.myplan.MyPlanRepository
import co.kr.bemyplan.data.repository.main.myplan.MyPlanRepositoryImpl
import co.kr.bemyplan.data.repository.main.scrap.ScrapRepository
import co.kr.bemyplan.data.repository.main.scrap.ScrapRepositoryImpl
import co.kr.bemyplan.data.repository.purchase.after.PlanDetailRepositoryImpl
import co.kr.bemyplan.data.repository.purchase.after.moveInfo.MoveInfoRepositoryImpl
import co.kr.bemyplan.data.repository.purchase.preview.PreviewRepositoryImpl
import co.kr.bemyplan.data.repository.scrap.PostScrapRepository
import co.kr.bemyplan.data.repository.scrap.PostScrapRepositoryImpl
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
        scrapListService: ScrapListService
    ): ScrapRepository {
        return ScrapRepositoryImpl(scrapListService)
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
    fun providePostScrapRepository(
        postScrapService: PostScrapService
    ): PostScrapRepository {
        return PostScrapRepositoryImpl(postScrapService)
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