package co.kr.bemyplan.data.entity.main.myplan

data class ResponseMyPlan(
    val data: Data
) {
    data class Data(
        val items: List<MyModel>,
        val totalCount: Int,
        val totalPage: Int
    )
}
