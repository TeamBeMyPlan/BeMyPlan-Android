package co.kr.bemyplan.di

import android.app.Application
import android.content.Context
import co.kr.bemyplan.data.firebase.FirebaseAnalyticsProvider
import co.kr.bemyplan.data.local.BeMyPlanDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SingletonModule {
    @Provides
    @Singleton
    @ApplicationContext
    fun provideApplication(application: Application) = application

    @Provides
    @Singleton
    fun provideBeMyPlanDataStore(@ApplicationContext context: Context) = BeMyPlanDataStore(context)

    @Provides
    @Singleton
    fun provideFirebaseAnalytics() = FirebaseAnalyticsProvider()
}