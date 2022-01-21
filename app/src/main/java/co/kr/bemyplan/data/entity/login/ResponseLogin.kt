package co.kr.bemyplan.data.entity.login

data class ResponseLogin(
    val data: Data,
    val statusCode: Int,
    val message: String
) {
    data class Data(
        val userInfo: UserInfoModel
    )
}
