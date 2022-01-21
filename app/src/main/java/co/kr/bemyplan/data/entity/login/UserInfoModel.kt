package co.kr.bemyplan.data.entity.login

import com.google.gson.annotations.SerializedName

data class UserInfoModel(
    val nickname: String,
    @SerializedName("access_token")
    val accessToken: String
)
