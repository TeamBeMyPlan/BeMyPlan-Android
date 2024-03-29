package co.kr.bemyplan.ui.onboarding

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewpagerFragmentAdapter(fragmentActivity : FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    val fragmentList = listOf<Fragment>(FirstOnboardingFragment(), SecondOnboardingFragment(), ThirdOnboardingFragment())

    override fun getItemCount():Int{
        return fragmentList.size
    }

    override fun createFragment(position:Int):Fragment{
        return fragmentList[position]
    }
}