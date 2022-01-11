package co.kr.bemyplan.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import co.kr.bemyplan.R
import co.kr.bemyplan.databinding.ActivityMainBinding
import co.kr.bemyplan.ui.main.scrap.ScrapFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        initBottomNavigation()
    }

    private fun initBottomNavigation() {
        supportFragmentManager.beginTransaction()
            .add(R.id.fcv_main, HomeFragment())
            .commit()
        binding.bnv.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.menu_home -> {
                    replaceFragment(HOME_FRAGMENT)
                    return@setOnItemSelectedListener true
                }
                R.id.menu_location -> {
                    replaceFragment(LOCATION_FRAGMENT)
                    return@setOnItemSelectedListener true
                }
                R.id.menu_scrap -> {
                    replaceFragment(SCRAP_FRAGMENT)
                    return@setOnItemSelectedListener true
                }
                R.id.menu_my_page -> {
                    replaceFragment(MY_PLAN_FRAGMENT)
                    return@setOnItemSelectedListener true
                }
                else -> return@setOnItemSelectedListener false
            }
        }
    }

    private fun replaceFragment(fragmentType: Int) {
        val transaction = supportFragmentManager.beginTransaction()
        when (fragmentType) {
            HOME_FRAGMENT -> transaction.replace(R.id.fcv_main, HomeFragment())
            LOCATION_FRAGMENT -> transaction.replace(R.id.fcv_main, LocationFragment())
            SCRAP_FRAGMENT -> transaction.replace(R.id.fcv_main, ScrapFragment())
            MY_PLAN_FRAGMENT -> transaction.replace(R.id.fcv_main, MyPageFragment())
        }
        transaction.commit()
    }

    companion object {
        const val HOME_FRAGMENT = 0
        const val LOCATION_FRAGMENT = 1
        const val SCRAP_FRAGMENT = 2
        const val MY_PLAN_FRAGMENT = 3
    }
}