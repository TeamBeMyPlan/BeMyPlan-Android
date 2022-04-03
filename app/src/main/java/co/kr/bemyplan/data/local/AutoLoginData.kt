package co.kr.bemyplan.data.local

import android.content.Context
import android.content.SharedPreferences

object AutoLoginData {
    private const val STORAGE_KEY = "USER_AUTH"
    private const val AUTO_LOGIN = "AUTO_LOGIN"
    private const val SESSION_ID = "SESSION_ID"
    private const val USER_ID = "0"

    fun getAutoLogin(context: Context): Boolean {
        return getSharedPreference(context).getBoolean(AUTO_LOGIN, false)
    }

    fun getAccessToken(context: Context): String {
        return getSharedPreference(context).getString(SESSION_ID, "") ?: ""
    }

    fun getNickname(context: Context): String {
        return getSharedPreference(context).getString(USER_ID, "") ?: ""
    }

    fun setAutoLogin(context: Context, value: Boolean, sessionId: String, userId: Int) {
        getSharedPreference(context).edit()
            .putBoolean(AUTO_LOGIN, value)
            .putString(SESSION_ID, sessionId)
            .putInt(USER_ID, userId)
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