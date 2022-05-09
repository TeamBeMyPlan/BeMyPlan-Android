package co.kr.bemyplan.domain.model.main.location

data class LocationData(
    val locked : Boolean,
    val name : String,
    val region : String,
    val thumbnailUrl : String
)