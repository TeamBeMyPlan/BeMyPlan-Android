package co.kr.bemyplan.data.entity.purchase.after

data class Post(
    val author: String,
    val title: String,
    val spots: List<List<Spot>>,
    val total_days: Int
) {
}
