package co.kr.bemyplan.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import co.kr.bemyplan.R
import co.kr.bemyplan.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {
    private lateinit var binding : ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)

        Handler(Looper.getMainLooper()).postDelayed({
            val fadeAnim : Animation = AnimationUtils.loadAnimation(this, R.anim.splash_animation)
            binding.imageView.startAnimation(fadeAnim)
        }, 1000L)

        Handler(Looper.getMainLooper()).postDelayed({
            val fadeAnim : Animation = AnimationUtils.loadAnimation(this, R.anim.splash_animation)
            binding.imageView2.startAnimation(fadeAnim)
        }, 1000L)

        setContentView(binding.root)
    }
}