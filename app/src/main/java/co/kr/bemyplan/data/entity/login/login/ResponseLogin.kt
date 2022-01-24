package co.kr.bemyplan.data.entity.login.login

import co.kr.bemyplan.data.entity.login.UserInfoModel

data class ResponseLogin(
    val data: UserInfoModel,
    val statusCode: Int,
    val message: String
)