package co.kr.bemyplan.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.viewpager2.widget.ViewPager2
import co.kr.bemyplan.R
import co.kr.bemyplan.databinding.ActivityMainBinding
import co.kr.bemyplan.ui.main.adapter.MainViewPagerAdapter

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewPagerAdapter: MainViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.vp.isUserInputEnabled=false
        initViewPagerAdapter()
        initBottomNavigation()
    }

    private fun initViewPagerAdapter() {
        viewPagerAdapter = MainViewPagerAdapter(this)
        viewPagerAdapter.fragments.addAll(
            listOf(
                HomeFragment(),
                LocationFragment(),
                ScrapFragment(),
                MyPageFragment()
            )
        )
        binding.vp.adapter = viewPagerAdapter
    }

    private fun initBottomNavigation() {
        binding.vp.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                binding.bnv.menu.getItem(position).isChecked = true
            }
        })
        binding.bnv.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.menu_first -> {
                    binding.vp.currentItem = FIRST_FRAGMENT
                    return@setOnItemSelectedListener true
                }
                R.id.menu_second -> {
                    binding.vp.currentItem = SECOND_FRAGMENT
                    return@setOnItemSelectedListener true
                }
                R.id.menu_third -> {
                    binding.vp.currentItem = THIRD_FRAGMENT
                    return@setOnItemSelectedListener true
                }
                else -> {
                    binding.vp.currentItem = FOURTH_FRAGMENT
                    return@setOnItemSelectedListener true
                }
            }
        }
    }

    companion object {
        const val FIRST_FRAGMENT = 0
        const val SECOND_FRAGMENT = 1
        const val THIRD_FRAGMENT = 2
        const val FOURTH_FRAGMENT = 3
    }
}