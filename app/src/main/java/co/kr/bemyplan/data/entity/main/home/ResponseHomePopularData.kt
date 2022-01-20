package co.kr.bemyplan.data.entity.main.home

import com.google.gson.annotations.SerializedName

data class ResponseHomePopularData(
    val data : List<Data>
){
    data class Data(
        val id : Int,
        @SerializedName("thumbnail_url")
        val thumbnailUrl : String,
        val title : String
    )
}
