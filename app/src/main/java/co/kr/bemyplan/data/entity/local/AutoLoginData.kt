package co.kr.bemyplan.data.entity.local

import android.content.Context
import android.content.SharedPreferences

object AutoLoginData {
    private const val STORAGE_KEY = "USER_AUTH"
    private const val AUTO_LOGIN = "AUTO_LOGIN"
    private const val ACCESS_TOKEN = "ACCESS_TOKEN"
    private const val NICKNAME = "NICKNAME"

    fun getAutoLogin(context: Context): Boolean {
        return getSharedPreference(context).getBoolean(AUTO_LOGIN, false)
    }

    fun getAccessToken(context: Context): String {
        return getSharedPreference(context).getString(ACCESS_TOKEN, "") ?: ""
    }

    fun getNickname(context: Context): String {
        return getSharedPreference(context).getString(NICKNAME, "") ?: ""
    }

    fun setAutoLogin(context: Context, value: Boolean, accessToken: String, nickname: String) {
        getSharedPreference(context).edit()
            .putBoolean(AUTO_LOGIN, value)
            .putString(ACCESS_TOKEN, accessToken)
            .putString(NICKNAME, nickname)
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

    private fun getSharedPreference(context: Context): SharedPreferences {
        return context.getSharedPreferences(STORAGE_KEY, Context.MODE_PRIVATE)
    }
}