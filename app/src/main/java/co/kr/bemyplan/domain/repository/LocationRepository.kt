package co.kr.bemyplan.domain.repository

import co.kr.bemyplan.domain.model.main.location.LocationData

interface LocationRepository {
    suspend fun getLocationData():List<LocationData>
}