package co.kr.bemyplan.data.entity.login.signup

data class RequestSignUp(
    val token: String,
    val socialType: String,
    val nickname: String,
    val email: String
)