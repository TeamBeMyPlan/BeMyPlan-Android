package co.kr.bemyplan.data.repository.main.home

import co.kr.bemyplan.data.api.HomeNewService
import co.kr.bemyplan.domain.model.main.home.HomeDomainData
import co.kr.bemyplan.domain.repository.HomeNewRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class HomeNewRepositoryImpl @Inject constructor(
    private val homeNewService : HomeNewService,
    private val coroutineDispatcher: CoroutineDispatcher
) : HomeNewRepository {
    override suspend fun getHomeNewData(): List<HomeDomainData> {
        return withContext(coroutineDispatcher){
            homeNewService.getNewData().data.contents
        }
    }
}