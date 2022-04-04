package co.kr.bemyplan.data.entity.main.location

data class ResponseLocationData(
    val message : String,
    val resultCode : String,
    val data : List<LocationData>
){
    data class LocationData(
        val locked : Boolean,
        val name : String,
        val region : String,
        val thumbnailUrl : String
    )
}
