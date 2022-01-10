package co.kr.bemyplan.data

data class DailyContents(
    val placeName: List<String>,
    val address: List<String>,
    val image: List<String>,
    val context: List<String>,
    val transportation: List<String>,
    val duration: List<Int>
)
