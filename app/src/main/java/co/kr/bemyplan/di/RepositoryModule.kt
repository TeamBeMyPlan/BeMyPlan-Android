package co.kr.bemyplan.di

import co.kr.bemyplan.data.api.NewListService
import co.kr.bemyplan.data.api.PreviewService
import co.kr.bemyplan.data.api.ScrapListService
import co.kr.bemyplan.data.repository.list.latest.NewListRepository
import co.kr.bemyplan.data.repository.list.latest.NewListRepositoryImpl
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

    // 최신 여행 일정 뷰
    @ViewModelScoped
    @Provides
    fun provideNewListRepository(
        newListService: NewListService
    ): NewListRepository {
        return NewListRepositoryImpl(newListService)
    }
}