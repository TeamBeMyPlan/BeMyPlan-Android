package co.kr.bemyplan.data.entity.login.login

import co.kr.bemyplan.domain.model.login.UserInfoModel

data class ResponseLogin(
    val data: UserInformation,
    val resultCode: String,
    val message: String
) {
    data class UserInformation(
        val nickname: String,
        val sessionId: String,
        val userId: Int
    ) {
        fun toModel(): UserInfoModel = UserInfoModel(
            nickname = this.nickname,
            sessionId = this.sessionId,
            userId = this.userId
        )
    }
}