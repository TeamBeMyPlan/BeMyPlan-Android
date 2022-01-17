package co.kr.bemyplan.data.local

import android.content.Context
import android.content.SharedPreferences

object AutoLoginData {
    private const val STORAGE_KEY = "USER_AUTH"
    private const val AUTO_LOGIN = "AUTO_LOGIN"
    private const val ACCESS_TOKEN = "ACCESS_TOKEN"

    fun getAutoLogin(context: Context): Boolean {
        return getSharedPreference(context).getBoolean(AUTO_LOGIN, false)
    }

    fun getAccessToken(context: Context): String {
        return getSharedPreference(context).getString(ACCESS_TOKEN, "") ?: ""
    }

//    fun getRefreshToken(context: Context): String {
//        return getSharedPreference(context).getString(REFRESH_TOKEN, "") ?: ""
//    }

    fun setAutoLogin(context: Context, value: Boolean, access_token: String, refresh_token: String) {
        getSharedPreference(context).edit()
            .putBoolean(AUTO_LOGIN, value)
            .putString(ACCESS_TOKEN, access_token)
            .apply()
    }

    fun removeAutoLogin(context: Context) {
        getSharedPreference(context).edit()
            .remove(AUTO_LOGIN)
            .apply()
    }

    fun clearStorage(context: Context) {
        getSharedPreference(context).edit()
            .clear()
            .apply()
    }

    fun getSharedPreference(context: Context): SharedPreferences {
        return context.getSharedPreferences(STORAGE_KEY, Context.MODE_PRIVATE)
    }
}