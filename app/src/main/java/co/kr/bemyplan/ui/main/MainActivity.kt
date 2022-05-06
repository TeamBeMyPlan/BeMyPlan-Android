package co.kr.bemyplan.ui.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import co.kr.bemyplan.R
import co.kr.bemyplan.data.local.FirebaseDefaultEventParameters
import co.kr.bemyplan.databinding.ActivityMainBinding
import co.kr.bemyplan.ui.sort.viewmodel.SortViewModel
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val sortViewModel by viewModels<SortViewModel>()
    private val fb = Firebase.analytics.apply {
        setDefaultEventParameters(FirebaseDefaultEventParameters.parameters)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        initBottomNavigation()
    }

    private fun initBottomNavigation() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fcv_main) as NavHostFragment
        val navController = navHostFragment.findNavController()
        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            val bundle = Bundle()
            when (destination.id) {
                R.id.fragment_home -> bundle.putString("source", "홈")
                R.id.fragment_location -> bundle.putString("source", "여행지")
                R.id.fragment_scrap -> bundle.putString("source", "스크랩")
                R.id.fragment_my_plan -> bundle.putString("source", "마이플랜")
            }
            fb.logEvent("clickTab", bundle)
        }
        binding.bnv.setupWithNavController(navController)
    }
}