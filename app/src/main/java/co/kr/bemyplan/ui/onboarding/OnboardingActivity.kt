package co.kr.bemyplan.ui.onboarding

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import co.kr.bemyplan.R
import co.kr.bemyplan.data.local.AutoLoginData
import co.kr.bemyplan.data.local.OnBoardingData
import co.kr.bemyplan.databinding.ActivityMainBindingImpl
import co.kr.bemyplan.databinding.ActivityOnboardingBinding
import co.kr.bemyplan.ui.login.LoginActivity
import co.kr.bemyplan.ui.main.MainActivity

class OnboardingActivity : AppCompatActivity() {
    private lateinit var binding : ActivityOnboardingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityOnboardingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.vpOnboarding.adapter = ViewpagerFragmentAdapter(this)
    }

    fun next(){
        val current = binding.vpOnboarding.currentItem
        binding.vpOnboarding.setCurrentItem(current+1, true)
        Log.d("next", "버튼이 눌렸습니다.")
    }
}