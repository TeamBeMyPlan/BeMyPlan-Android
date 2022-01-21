package co.kr.bemyplan.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import co.kr.bemyplan.R
import co.kr.bemyplan.databinding.ActivitySplashBinding
import co.kr.bemyplan.ui.login.LoginActivity

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
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }, 1500L)

        setContentView(binding.root)
    }
}