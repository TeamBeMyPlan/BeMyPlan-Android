package co.kr.bemyplan.ui.onboarding

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import co.kr.bemyplan.data.local.AutoLoginData
import co.kr.bemyplan.data.local.OnBoardingData
import co.kr.bemyplan.databinding.FragmentOnboarding3Binding
import co.kr.bemyplan.ui.login.LoginActivity
import co.kr.bemyplan.ui.main.MainActivity

class OnboardingFragment3 : Fragment() {
    private var _binding : FragmentOnboarding3Binding? = null
    private val binding get() = _binding ?: error("Binding이 초기화 되지 않았습니다.")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOnboarding3Binding.inflate(layoutInflater)
        val display = activity?.applicationContext?.resources?.displayMetrics
        val deviceWidth = display?.widthPixels
        val deviceHeight = display?.heightPixels

        val ivWidthRatio: Double = 230/360.0
        val ivHeightRatio : Double = 498/760.0
        val pageWidth = ivWidthRatio*deviceWidth!!
        val pageHeight = ivHeightRatio*deviceHeight!!
        binding.ivOnboarding.layoutParams.width = pageWidth.toInt()
        binding.ivOnboarding.layoutParams.height = pageHeight.toInt()

        binding.tvStart.setOnClickListener{
            Log.d("onboardinglog", "스타트 클릭")
            OnBoardingData.setOnBoarding(requireContext(), true)
            Log.d("logincheck", "${AutoLoginData.getAutoLogin(requireContext())}")
            checkAutoLogin()
        }

        binding.tvPass.setOnClickListener{
            (activity as OnboardingActivity).checkAutoLogin()
            OnBoardingData.setOnBoarding(requireContext(), true)
        }

        return binding.root
    }

    fun checkAutoLogin() {
        if (AutoLoginData.getAutoLogin(requireContext())) {
            val intent = Intent(requireContext(), MainActivity::class.java)
            startActivity(intent)
            Log.d("logincheck", "${AutoLoginData.getAutoLogin(requireContext())}")
            activity?.finish()
        } else {
            val intent = Intent(requireContext(), LoginActivity::class.java)
            startActivity(intent)
            Log.d("logincheck", "${AutoLoginData.getAutoLogin(requireContext())}")
            activity?.finish()
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}