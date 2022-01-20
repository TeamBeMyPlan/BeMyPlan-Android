package co.kr.bemyplan.data.entity.main.home

data class ResponseHomeData(
    val data: ResponseHomeItems,
    val totalPage : Int
){
    data class ResponseHomeItems(
        val items: List<HomeData>
    ){
        data class HomeData(
            val id: Int,
            val thumbnail_url: String,
            val title: String,
            val price: Int,
            val nickname: String
        )
    }
}