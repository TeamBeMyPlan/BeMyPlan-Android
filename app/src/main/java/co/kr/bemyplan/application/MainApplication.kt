package co.kr.bemyplan.application

import android.app.Application
import android.util.Log
import co.kr.bemyplan.BuildConfig
import co.kr.bemyplan.data.local.OnboardingSharedpref
import com.kakao.sdk.common.KakaoSdk
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MainApplication: Application() {
    override fun onCreate() {
        prefs= OnboardingSharedpref(applicationContext)
        super.onCreate()
        KakaoSdk.init(this, BuildConfig.KAKAO_APP_KEY)
    }

    companion object{
        lateinit var prefs : OnboardingSharedpref
    }
}