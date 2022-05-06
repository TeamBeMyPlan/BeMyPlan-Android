package co.kr.bemyplan.data.entity.login.login

import com.google.gson.annotations.SerializedName

data class ResponseGoogleLogin(
//    "access_token": "ya29.A0ARrdaM8cJdHOKsirScD5bvBXNdwlhmkN0j4RZnOlK_qawfRODNuNechlHujbGwtsSEsBQK1o_TPT8QDJ3BHd2_LdLouaWy7afDSmbGlzHDapjswlHUAgRWPo5eOQf2TmoURtUdrmiHnTqvEoV6O--PPbtO207w",
//         "expires_in": 3598,
//         "scope": "openid https:\/\/www.googleapis.com\/auth\/userinfo.profile https:\/\/www.googleapis.com\/auth\/userinfo.email https:\/\/www.googleapis.com\/auth\/drive.appdata",
//         "token_type": "Bearer",
//         "id_token": "eyJhbGciOiJSUzI1NiIsImtpZCI6Ijg2MTY0OWU0NTAzMTUzODNmNmI5ZDUxMGI3Y2Q0ZTkyMjZjM2NkODgiLCJ0eXAiOiJKV1QifQ
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
