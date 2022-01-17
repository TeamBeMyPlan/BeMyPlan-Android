package co.kr.bemyplan.data.entity.main.home

data class ResponseHomePopularData(
    val data : List<Data>
){
    data class Data(
        val id : Int,
        val thumbnail_url : String,
        val title : String
    )
}
