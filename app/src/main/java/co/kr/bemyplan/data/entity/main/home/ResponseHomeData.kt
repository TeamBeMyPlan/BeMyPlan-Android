package co.kr.bemyplan.data.entity.main.home

data class ResponseHomeData(
    val data : List<HomeData>
){
    data class HomeData(
        val id : Int,
        val thumbnail_url : String,
        val title : String,
        val nickname : String
    )
}