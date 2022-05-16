package co.kr.bemyplan.data.repository.main.location

import co.kr.bemyplan.data.api.LocationService
import co.kr.bemyplan.domain.model.main.location.LocationData
import co.kr.bemyplan.domain.repository.LocationRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LocationRepositoryImpl @Inject constructor(
    private val locationservice:LocationService,
    private val coroutineDispatcher: CoroutineDispatcher
) : LocationRepository {
    override suspend fun getLocationData(): List<LocationData> {
        return withContext(coroutineDispatcher){
            locationservice.getLocation().data
        }
    }
}