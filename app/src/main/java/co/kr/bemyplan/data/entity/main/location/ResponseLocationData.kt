package co.kr.bemyplan.data.entity.main.location

import com.google.gson.annotations.SerializedName

data class ResponseLocationData(
    val data : List<LocationData>
){
    data class LocationData(
        val id : Int,
        val name : String,
        val photo_url : String,
        @SerializedName("is_activated")
        val isActivated : Boolean
    )
}
