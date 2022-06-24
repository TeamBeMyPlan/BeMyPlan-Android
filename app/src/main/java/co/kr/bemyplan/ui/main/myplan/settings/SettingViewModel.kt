package co.kr.bemyplan.ui.main.myplan.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.kr.bemyplan.data.firebase.FirebaseAnalyticsProvider
import co.kr.bemyplan.data.local.BeMyPlanDataStore
import co.kr.bemyplan.domain.repository.LogoutRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val logoutRepository: LogoutRepository,
    val firebaseAnalyticsProvider: FirebaseAnalyticsProvider
) : ViewModel() {
    @Inject
    lateinit var dataStore: BeMyPlanDataStore

    fun logout() {
        viewModelScope.launch {
            runCatching {
                logoutRepository.logout()
            }.onSuccess {
                dataStore.sessionId = ""
                dataStore.userId = 0
                dataStore.nickname = ""
            }.onFailure {
                Timber.e(it)
            }
        }
    }
}