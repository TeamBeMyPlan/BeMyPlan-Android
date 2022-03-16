package co.kr.bemyplan.ui.onboarding

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import co.kr.bemyplan.application.MainApplication
import co.kr.bemyplan.databinding.FragmentOnboarding1Binding

class OnboardingFragment1 : Fragment() {
    private var _binding : FragmentOnboarding1Binding? = null
    private val binding get() = _binding ?: error("Binding이 초기화 되지 않았습니다.")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOnboarding1Binding.inflate(layoutInflater)
        val display = activity?.applicationContext?.resources?.displayMetrics
        val deviceWidth = display?.widthPixels
        val deviceHeight = display?.heightPixels

        val ivWidthRatio: Double = 230/360.0
        val ivHeightRatio : Double = 498/760.0
        val pageWidth = ivWidthRatio*deviceWidth!!
        val pageHeight = ivHeightRatio*deviceHeight!!
        binding.ivOnboarding.layoutParams.width = pageWidth.toInt()
        binding.ivOnboarding.layoutParams.height = pageHeight.toInt()

        binding.tvNext.setOnClickListener{
            (activity as OnboardingActivity).next()
        }

        binding.tvPass.setOnClickListener{
            MainApplication.prefs.setOnBoarding(true)
            (activity as OnboardingActivity).checkAutoLogin()
        }

        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}