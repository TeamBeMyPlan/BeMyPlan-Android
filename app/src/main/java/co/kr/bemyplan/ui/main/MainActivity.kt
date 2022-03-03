package co.kr.bemyplan.ui.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.TooltipCompat
import androidx.core.view.forEach
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import co.kr.bemyplan.R
import co.kr.bemyplan.databinding.ActivityMainBinding
import co.kr.bemyplan.ui.main.home.HomeFragment
import co.kr.bemyplan.ui.main.location.LocationFragment
import co.kr.bemyplan.ui.main.myplan.MyPlanFragment
import co.kr.bemyplan.ui.main.scrap.ScrapFragment
import co.kr.bemyplan.ui.sort.viewmodel.SortViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val sortViewModel by viewModels<SortViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        initBottomNavigation()
    }

    private fun initBottomNavigation() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fcv_main) as NavHostFragment
        val navController = navHostFragment.findNavController()
        binding.bnv.setupWithNavController(navController)
    }
}