package co.kr.bemyplan.di

import co.kr.bemyplan.data.api.PreviewService
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
    @ViewModelScoped
    @Provides
    fun providePreviewRepository(
        previewService: PreviewService
    ): PreviewRepository {
        return PreviewRepositoryImpl(previewService)
    }
}