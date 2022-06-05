package co.kr.bemyplan.ui.main.myplan.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.kr.bemyplan.domain.repository.LogoutRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val logoutRepository: LogoutRepository
) : ViewModel() {
    fun logout() {
        viewModelScope.launch {
            runCatching {
                logoutRepository.logout()
            }.onFailure {
                Timber.e(it)
            }
        }
    }
}