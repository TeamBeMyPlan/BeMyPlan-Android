package co.kr.bemyplan.data.entity.login.login

import com.google.gson.annotations.SerializedName

data class RequestGoogleLogin(
    @SerializedName("grant_type")
    val grantType: String,
    @SerializedName("client_id")
    val clientId: String,
    @SerializedName("client_secret")
    val clientSecret: String,
    @SerializedName("redirect_uri")
    val redirectUri: String,
    val code: String
)
