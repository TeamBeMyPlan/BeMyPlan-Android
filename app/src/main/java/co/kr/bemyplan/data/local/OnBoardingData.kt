package co.kr.bemyplan.data.local

import android.content.Context
import android.content.SharedPreferences

object OnBoardingData {
    private const val ON_BOARDING = "ON_BOARDING"

    fun getOnBoarding(context: Context):Boolean{
        return getSharedPreferences(context).getBoolean(ON_BOARDING, false)
    }

    fun setOnBoarding(context: Context, value:Boolean){
        getSharedPreferences(context).edit()
            .putBoolean(ON_BOARDING, value)
            .apply()
    }

    fun clearStorage(context: Context){
        getSharedPreferences(context).edit()
            .clear().apply()
    }

    private fun getSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(ON_BOARDING, Context.MODE_PRIVATE)
    }
}