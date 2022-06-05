package co.kr.bemyplan.ui.main.myplan.settings.withdrawal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.kr.bemyplan.data.local.BeMyPlanDataStore
import co.kr.bemyplan.domain.repository.LogoutRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class WithdrawalViewModel @Inject constructor(
    private val logoutRepository: LogoutRepository
) : ViewModel() {
    @Inject
    lateinit var dataStore: BeMyPlanDataStore

    fun signOut(reason: String) {
        viewModelScope.launch {
            runCatching {
                logoutRepository.signOut(reason)
            }.onSuccess {
                dataStore.sessionId = ""
                dataStore.userId = 0
            }.onFailure {
                Timber.e(it)
            }
        }
    }
}