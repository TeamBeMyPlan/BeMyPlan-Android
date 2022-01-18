package co.kr.bemyplan.data.entity.main.location

data class ResponseLocationData(
    val data : List<LocationData>
){
    data class LocationData(
        val id : Int,
        val name : String,
        val photo_url : String,
        val is_activated : Boolean
    )
}
