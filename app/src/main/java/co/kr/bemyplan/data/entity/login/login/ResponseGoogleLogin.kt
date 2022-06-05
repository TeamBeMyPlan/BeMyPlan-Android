package co.kr.bemyplan.data.entity.login.login

import com.google.gson.annotations.SerializedName

data class ResponseGoogleLogin(
    @SerializedName("access_token")
    val accessToken: String,
    @SerializedName("expires_in")
    val expiresIn: Int,
    @SerializedName("scope")
    val scope: String,
    @SerializedName("token_type")
    val tokenType: String,
    @SerializedName("id_token")
    val idToken: String
)
