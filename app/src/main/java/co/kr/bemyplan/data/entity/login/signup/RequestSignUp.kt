package co.kr.bemyplan.data.entity.login.signup

import com.google.gson.annotations.SerializedName

data class RequestSignUp(
    @SerializedName("social_token")
    val socialToken: String,
    @SerializedName("social_type")
    val socialType: String,
    val nickname: String
)