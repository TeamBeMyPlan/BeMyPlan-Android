package co.kr.bemyplan.data.entity.main.myplan.logout

import co.kr.bemyplan.data.entity.login.UserInfoModel

data class ResponseLogout(
    val data: UserInfoModel,
    val message: String,
    val resultCode: String
)
