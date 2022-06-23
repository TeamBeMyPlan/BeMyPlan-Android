package co.kr.bemyplan.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import co.kr.bemyplan.R
import co.kr.bemyplan.data.firebase.FirebaseAnalyticsProvider
import co.kr.bemyplan.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var firebaseAnalyticsProvider: FirebaseAnalyticsProvider

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        initBottomNavigation()
    }

    private fun initBottomNavigation() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fcv_main) as NavHostFragment
        val navController = navHostFragment.findNavController()
        navController.addOnDestinationChangedListener { _, destination, _ ->
            val bundle = Bundle()
            when (destination.id) {
                R.id.fragment_home -> bundle.putString("source", "홈")
                R.id.fragment_location -> bundle.putString("source", "여행지")
                R.id.fragment_scrap -> bundle.putString("source", "스크랩")
                R.id.fragment_my_plan -> bundle.putString("source", "마이플랜")
            }
            firebaseAnalyticsProvider.firebaseAnalytics.logEvent("clickTab", bundle)
        }
        binding.bnv.setupWithNavController(navController)
    }
}