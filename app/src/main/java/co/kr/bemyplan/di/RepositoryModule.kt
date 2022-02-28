package co.kr.bemyplan.di

import co.kr.bemyplan.data.api.*
import co.kr.bemyplan.data.repository.list.latest.LatestListRepository
import co.kr.bemyplan.data.repository.list.latest.LatestListRepositoryImpl
import co.kr.bemyplan.data.repository.list.location.LocationListRepository
import co.kr.bemyplan.data.repository.list.location.LocationListRepositoryImpl
import co.kr.bemyplan.data.repository.list.suggest.SuggestListRepository
import co.kr.bemyplan.data.repository.list.suggest.SuggestListRepositoryImpl
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
}