package co.kr.bemyplan.data.entity.login.login

import com.google.gson.annotations.SerializedName

data class RequestLogin(
    @SerializedName("social_token")
    val socialToken: String,
    @SerializedName("social_type")
    val socialType: String
)
