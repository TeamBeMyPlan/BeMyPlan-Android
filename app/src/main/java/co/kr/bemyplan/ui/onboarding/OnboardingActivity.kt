package co.kr.bemyplan.ui.onboarding

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import co.kr.bemyplan.R
import co.kr.bemyplan.data.local.AutoLoginData
import co.kr.bemyplan.data.local.BeMyPlanDataStore
import co.kr.bemyplan.databinding.*
import co.kr.bemyplan.ui.login.LoginActivity
import co.kr.bemyplan.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class OnboardingActivity : AppCompatActivity() {
    @Inject
    lateinit var dataStore: BeMyPlanDataStore
    private lateinit var binding : ActivityOnboardingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityOnboardingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.vpOnboarding.adapter = ViewpagerFragmentAdapter(this)
    }

    fun next(){
        var current = binding.vpOnboarding.currentItem
        Log.d("onboardinglog", "클릭되었습니다")
        binding.vpOnboarding.setCurrentItem(current+1, true)
    }

    fun checkAutoLogin() {
        if(dataStore.sessionId != "") {
            val intent = Intent(this, MainActivity::class.java)
            Log.d("autologincheck", "메인 액티비티로")
            startActivity(intent)
            finish()
        } else {
            val intent = Intent(this, LoginActivity::class.java)
            Log.d("autologincheck", "로그인 액티비티로")
            startActivity(intent)
            finish()
        }

//        TODO: Test 후 삭제요망
//        if (AutoLoginData.getAutoLogin(this)) {
//            val intent = Intent(this, MainActivity::class.java)
//            Log.d("autologincheck", "메인 액티비티로")
//            startActivity(intent)
//            finish()
//        } else {
//            val intent = Intent(this, LoginActivity::class.java)
//            Log.d("autologincheck", "로그인 액티비티로")
//            startActivity(intent)
//            finish()
//        }
    }

}