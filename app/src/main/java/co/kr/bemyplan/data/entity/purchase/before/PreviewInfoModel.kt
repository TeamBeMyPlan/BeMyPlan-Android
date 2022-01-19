package co.kr.bemyplan.data.entity.purchase.before

data class PreviewInfoModel(
    val post_id: Int,
    val nickname: String,
    val title: String,
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