package co.kr.bemyplan.ui.main

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import co.kr.bemyplan.R
import co.kr.bemyplan.application.MainApplication
import co.kr.bemyplan.data.local.AutoLoginData
import co.kr.bemyplan.data.local.BeMyPlanDataStore
import co.kr.bemyplan.data.local.FirebaseDefaultEventParameters
import co.kr.bemyplan.databinding.ActivitySplashBinding
import co.kr.bemyplan.ui.login.LoginActivity
import co.kr.bemyplan.ui.onboarding.OnboardingActivity
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    @Inject
    lateinit var dataStore: BeMyPlanDataStore
    private lateinit var binding : ActivitySplashBinding
    private lateinit var firebaseAnalytics: FirebaseAnalytics

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)

        // firebase event logging
        firebaseAnalytics = Firebase.analytics
        firebaseAnalytics.setDefaultEventParameters(FirebaseDefaultEventParameters.parameters)
        firebaseAnalytics.logEvent("appFirstOpen", null)

        val display = this.resources.displayMetrics
        val deviceWidth = display.widthPixels
        val ratio : Double = 100/360.0
        val imagePadding : Int = (ratio * deviceWidth).toInt()
        binding.imageView.setPadding(imagePadding, 0, imagePadding, 0)

        Handler(Looper.getMainLooper()).postDelayed({
            val fadeAnim : Animation = AnimationUtils.loadAnimation(this, R.anim.splash_animation)
            binding.imageView.startAnimation(fadeAnim)
            checkOnBoarding()
        }, 1500L)

        setContentView(binding.root)
    }

    private fun checkOnBoarding(){
        if(MainApplication.prefs.getOnBoarding()){
            checkAutoLogin()
        }
        else{
            val intent = Intent(this, OnboardingActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun checkAutoLogin() {
        if(dataStore.sessionId != "") {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

//        TODO: Test 후 삭제요망
//        if(AutoLoginData.getAutoLogin(this)) {
//            val intent = Intent(this, MainActivity::class.java)
//            startActivity(intent)
//            finish()
//        } else {
//            val intent = Intent(this, LoginActivity::class.java)
//            startActivity(intent)
//            finish()
//        }
    }
}