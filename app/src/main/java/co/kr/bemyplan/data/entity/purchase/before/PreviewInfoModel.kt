package co.kr.bemyplan.data.entity.purchase.before

data class PreviewInfoModel(
    val author_id: Int,
    val author: String,
    val title: String,
    val price: Int,
    val description: String,
    val tag_theme: String,
    val tag_count_spot: Int,
    val tag_count_day: Int,
    val tag_count_restaurant: Int,
    val tag_partner: String,
    val tag_money: String,
    val tag_mobility: String,
    val tag_month: Int
)