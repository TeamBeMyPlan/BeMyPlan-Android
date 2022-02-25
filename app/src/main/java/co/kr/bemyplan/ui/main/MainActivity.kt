package co.kr.bemyplan.ui.main

import android.os.Bundle
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

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

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

//        binding.bnv.menu.forEach {
//            TooltipCompat.setTooltipText(findViewById(it.itemId), null)
//        }
    }

    private fun replaceFragment(fragmentType: Int) {
        val transaction = supportFragmentManager.beginTransaction()
        when (fragmentType) {
            HOME_FRAGMENT -> {
                transaction.replace(R.id.fcv_main, homeFragment)
            }
            LOCATION_FRAGMENT -> {
                transaction.replace(R.id.fcv_main, locationFragment)
            }
            SCRAP_FRAGMENT -> {
                transaction.replace(R.id.fcv_main, scrapFragment)
            }
            MY_PLAN_FRAGMENT -> {
                transaction.replace(R.id.fcv_main, myPlanFragment)
            }
        }
        transaction.commit()
    }

    fun moveHome() {
        binding.bnv.selectedItemId = R.id.fragment_home
        replaceFragment(HOME_FRAGMENT)
    }

    companion object {
        const val HOME_FRAGMENT = 0
        const val LOCATION_FRAGMENT = 1
        const val SCRAP_FRAGMENT = 2
        const val MY_PLAN_FRAGMENT = 3

        private val homeFragment = HomeFragment()
        private val locationFragment = LocationFragment()
        private val scrapFragment = ScrapFragment()
        private val myPlanFragment = MyPlanFragment()
    }
}