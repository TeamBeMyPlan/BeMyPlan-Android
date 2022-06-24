package co.kr.bemyplan.ui.onboarding

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import co.kr.bemyplan.data.firebase.FirebaseAnalyticsProvider
import co.kr.bemyplan.data.local.BeMyPlanDataStore
import co.kr.bemyplan.databinding.ActivityOnboardingBinding
import co.kr.bemyplan.ui.login.LoginActivity
import co.kr.bemyplan.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class OnboardingActivity : AppCompatActivity() {
    @Inject
    lateinit var dataStore: BeMyPlanDataStore
    private lateinit var binding: ActivityOnboardingBinding

    @Inject
    lateinit var firebaseAnalyticsProvider: FirebaseAnalyticsProvider

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnboardingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseAnalyticsProvider.firebaseAnalytics.logEvent("onboardingFirstOpen", null)
        binding.vpOnboarding.adapter = ViewpagerFragmentAdapter(this)
    }

    fun next() {
        val current = binding.vpOnboarding.currentItem
        binding.vpOnboarding.setCurrentItem(current + 1, true)
    }

    fun checkAutoLogin() {
        firebaseAnalyticsProvider.firebaseAnalytics.logEvent("onboardingEnd", null)
        if (dataStore.sessionId != "") {
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