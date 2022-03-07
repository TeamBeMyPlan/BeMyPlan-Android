package co.kr.bemyplan.di

import co.kr.bemyplan.data.api.*
import co.kr.bemyplan.data.repository.list.latest.LatestListRepository
import co.kr.bemyplan.data.repository.list.latest.LatestListRepositoryImpl
import co.kr.bemyplan.data.repository.list.location.LocationListRepository
import co.kr.bemyplan.data.repository.list.location.LocationListRepositoryImpl
import co.kr.bemyplan.data.repository.list.suggest.SuggestListRepository
import co.kr.bemyplan.data.repository.list.suggest.SuggestListRepositoryImpl
import co.kr.bemyplan.data.repository.list.userpost.UserPostListRepository
import co.kr.bemyplan.data.repository.list.userpost.UserPostListRepositoryImpl
import co.kr.bemyplan.data.repository.login.LoginRepository
import co.kr.bemyplan.data.repository.login.LoginRepositoryImpl
import co.kr.bemyplan.data.repository.main.myplan.MyPlanRepository
import co.kr.bemyplan.data.repository.main.myplan.MyPlanRepositoryImpl
import co.kr.bemyplan.data.repository.scrap.PostScrapRepository
import co.kr.bemyplan.data.repository.scrap.PostScrapRepositoryImpl
import co.kr.bemyplan.data.repository.main.scrap.ScrapRepository
import co.kr.bemyplan.data.repository.main.scrap.ScrapRepositoryImpl
import co.kr.bemyplan.data.repository.purchase.preview.PreviewRepository
import co.kr.bemyplan.data.repository.purchase.preview.PreviewRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {
    // 구매 전 여행일정 컨텐츠 뷰
    @ViewModelScoped
    @Provides
    fun providePreviewRepository(
        previewService: PreviewService
    ): PreviewRepository {
        return PreviewRepositoryImpl(previewService)
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
        latestListService: LatestListService
    ): LatestListRepository {
        return LatestListRepositoryImpl(latestListService)
    }

    // 비마플 추천 여행 일정 리스트 뷰
    @ViewModelScoped
    @Provides
    fun provideSuggestListRepository(
        suggestListService: SuggestListService
    ): SuggestListRepository {
        return SuggestListRepositoryImpl(suggestListService)
    }

    // 여행지 리스트 뷰
    @ViewModelScoped
    @Provides
    fun provideLocationListRepository(
        locationListService: LocationListService
    ): LocationListRepository {
        return LocationListRepositoryImpl(locationListService)
    }

    // 유저가 작성한 게시물 리스트 뷰
    @ViewModelScoped
    @Provides
    fun provideUserPostListRepository(
        userPostListService: UserPostListService
    ): UserPostListRepository {
        return UserPostListRepositoryImpl(userPostListService)
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
        loginService: LoginService
    ): LoginRepository {
        return LoginRepositoryImpl(loginService)
    }

    // 스크랩 버튼 클릭
    @ViewModelScoped
    @Provides
    fun providePostScrapRepository(
        postScrapService: PostScrapService
    ): PostScrapRepository {
        return PostScrapRepositoryImpl(postScrapService)
    }
}