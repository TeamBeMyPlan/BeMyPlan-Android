package co.kr.bemyplan.ui.main.myplan.settings.viewmodel

import android.content.SharedPreferences
import android.os.UserManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.kr.bemyplan.data.local.FirebaseDefaultEventParameters
import co.kr.bemyplan.domain.repository.LogoutRepository
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import com.kakao.sdk.auth.Constants.ACCESS_TOKEN
import com.kakao.sdk.user.UserApiClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val logoutRepository: LogoutRepository,
    private val localPreferences: SharedPreferences
) : ViewModel() {
    val fb = Firebase.analytics.apply {
        setDefaultEventParameters(FirebaseDefaultEventParameters.parameters)
    }

    private var _token = MutableLiveData<String>()
    val token: LiveData<String> get() = _token

    fun postKakaoLogout() {
        viewModelScope.launch {
            kotlin.runCatching {
                logoutRepository.postLogout()
            }.onSuccess {
                UserApiClient.instance.logout { error ->
                    if (error != null) {
                        Timber.tag("kakaoLogoutFail").e(error)
                    }else {
                    }
                }
                localPreferences.edit()
                    .remove(ACCESS_TOKEN)
                    .apply()
            }.onFailure {

            }
        }
    }
}