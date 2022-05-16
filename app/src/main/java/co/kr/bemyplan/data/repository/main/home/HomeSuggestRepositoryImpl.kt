package co.kr.bemyplan.data.repository.main.home

import co.kr.bemyplan.data.api.HomeSuggestService
import co.kr.bemyplan.domain.model.main.home.HomeDomainData
import co.kr.bemyplan.domain.repository.HomeSuggestRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class HomeSuggestRepositoryImpl @Inject constructor(
    private val homeSuggestService: HomeSuggestService,
    private val coroutineDispatcher: CoroutineDispatcher
) : HomeSuggestRepository {
    override suspend fun getHomeSuggestData(): List<HomeDomainData> {
        return withContext(coroutineDispatcher){
            homeSuggestService.getSuggestData().data.contents
        }
    }
}