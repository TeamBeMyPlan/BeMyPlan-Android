package co.kr.bemyplan.data.entity.purchase.after

data class Post(
    val author: String,
    val title: String,
    val spots: List<DailyContents>,
    val total_days: Int
) {
}
