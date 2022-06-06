package co.kr.bemyplan.data.entity.login.signup

import co.kr.bemyplan.domain.model.login.UserInfoModel

data class ResponseSignUp(
    val data: UserInfoModel,
    val resultCode: String,
    val message: String
)