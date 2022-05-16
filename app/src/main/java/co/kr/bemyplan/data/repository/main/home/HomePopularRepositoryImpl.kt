package co.kr.bemyplan.data.repository.main.home

import co.kr.bemyplan.data.api.HomePopularService
import co.kr.bemyplan.domain.model.main.home.HomeDomainData
import co.kr.bemyplan.domain.repository.HomePopularRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class HomePopularRepositoryImpl @Inject constructor(
    private val homePopularService: HomePopularService,
    private val coroutineDispatcher: CoroutineDispatcher
) : HomePopularRepository {
    override suspend fun getHomePopularData(): List<HomeDomainData> {
        return withContext(coroutineDispatcher){
            homePopularService.getPopularData().data.contents
        }
    }
}