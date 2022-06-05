package co.kr.bemyplan.data.repository.logout

import co.kr.bemyplan.data.api.LogoutService
import co.kr.bemyplan.data.entity.logout.RequestSignOut
import co.kr.bemyplan.domain.repository.LogoutRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LogoutRepositoryImpl @Inject constructor(
    private val logoutService: LogoutService,
    private val coroutineDispatcher: CoroutineDispatcher,
) : LogoutRepository {
    override suspend fun logout() {
        withContext(coroutineDispatcher) {
            logoutService.postLogout()
        }
    }

    override suspend fun signOut(reason: String) {
        withContext(coroutineDispatcher) {
            logoutService.deleteSignOut(RequestSignOut(reason))
        }
    }
}