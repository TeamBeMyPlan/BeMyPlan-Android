package co.kr.bemyplan.data.entity.login.check

data class ResponseDuplicatedNickname(
    val data: Data
) {
    data class Data(
        val duplicated: Boolean
    )
}