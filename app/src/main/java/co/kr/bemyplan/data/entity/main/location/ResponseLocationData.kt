package co.kr.bemyplan.data.entity.main.location

import com.google.gson.annotations.SerializedName

data class ResponseLocationData(
    val data : List<LocationData>
){
    data class LocationData(
        @SerializedName("area_id")
        val id : Int,
        val name : String,
        @SerializedName("photo_url")
        val photoUrl : String,
        @SerializedName("is_activated")
        val isActivated : Boolean
    )
}
