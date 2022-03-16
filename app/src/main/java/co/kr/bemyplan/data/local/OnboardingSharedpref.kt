package co.kr.bemyplan.data.local

import android.content.Context
import android.content.SharedPreferences
import android.util.Log

class OnboardingSharedpref(context: Context){
    private val onBoarding = "ON_BOARDING"
    private val prefs : SharedPreferences = context.getSharedPreferences(onBoarding, Context.MODE_PRIVATE)

    fun getOnBoarding():Boolean{
        return prefs.getBoolean(onBoarding, false)
    }

    fun setOnBoarding(value:Boolean) {
        prefs.edit().putBoolean(onBoarding, value).apply()
        Log.d("onboardinglog", "setOnboarding 실행됨")
    }
}