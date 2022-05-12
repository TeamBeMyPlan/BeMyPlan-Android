package co.kr.bemyplan.data.entity.main.location

import co.kr.bemyplan.domain.model.main.location.LocationData

data class ResponseLocationData(
    val message : String,
    val resultCode : String,
    val data : List<LocationData>
)
