package co.kr.bemyplan.data.local

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

const val FILE_NAME = "BEMYPLANDATASTORE"

class BeMyPlanDataStore @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val dataStore: SharedPreferences =
        context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)

    var sessionId: String
        set(value) = dataStore.edit { putString("SESSION_ID", value) }
        get() = dataStore.getString("SESSION_ID", "") ?: ""

    var userId: Int
        set(value) = dataStore.edit { putInt("USER_ID", value) }
        get() = dataStore.getInt("USER_ID", 0)
}