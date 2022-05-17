package co.kr.bemyplan.data.repository.main.myplan.logout

import co.kr.bemyplan.data.api.LogoutService
import co.kr.bemyplan.data.entity.main.myplan.logout.ResponseLogout
import co.kr.bemyplan.domain.repository.LogoutRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class LogoutRepositoryImpl(
    private val service: LogoutService,
    private val coroutineDispatcher: CoroutineDispatcher
) : LogoutRepository {
    override suspend fun postLogout(): ResponseLogout {
        return withContext(coroutineDispatcher) {
            service.postLogout()
        }
    }
}