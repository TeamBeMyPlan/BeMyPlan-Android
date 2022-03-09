package co.kr.bemyplan.ui.onboarding

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import co.kr.bemyplan.R
import co.kr.bemyplan.databinding.ActivityMainBindingImpl
import co.kr.bemyplan.databinding.ActivityOnboardingBinding

class OnboardingActivity : AppCompatActivity() {
    private lateinit var binding : ActivityOnboardingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityOnboardingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.vpOnboarding.adapter = ViewpagerFragmentAdapter(this)
    }
}