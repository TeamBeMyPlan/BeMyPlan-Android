package co.kr.bemyplan.domain.model.login

data class UserInfoModel(
    val nickname: String,
    val sessionId: String,
    val userId: Int
)
