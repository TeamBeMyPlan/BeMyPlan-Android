package co.kr.bemyplan.application

import android.app.Application
import co.kr.bemyplan.BuildConfig
import com.kakao.sdk.common.KakaoSdk

class MainApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        KakaoSdk.init(this, BuildConfig.KAKAO_APP_KEY)
    }
}