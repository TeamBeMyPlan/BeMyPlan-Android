package co.kr.bemyplan.ui.main

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import co.kr.bemyplan.R
import co.kr.bemyplan.data.local.AutoLoginData
import co.kr.bemyplan.data.local.OnBoardingData
import co.kr.bemyplan.databinding.ActivitySplashBinding
import co.kr.bemyplan.ui.login.LoginActivity
import co.kr.bemyplan.ui.onboarding.OnboardingActivity

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    private lateinit var binding : ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)

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
        if(OnBoardingData.getOnBoarding(this)){
            checkAutoLogin()
            Log.d("onboardingtest", "gotoAutoLogin")
        }
        else{
            val intent = Intent(this, OnboardingActivity::class.java)
            startActivity(intent)
            Log.d("onboardingtest", "gotoOnBoarding")
            finish()
        }

        val intent = Intent(this, OnboardingActivity::class.java)
        startActivity(intent)
        Log.d("onboardingtest", "gotoOnBoarding")
        finish()
    }

    private fun checkAutoLogin() {
        if(AutoLoginData.getAutoLogin(this)) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}