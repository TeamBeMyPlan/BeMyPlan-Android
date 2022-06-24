package co.kr.bemyplan.ui.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import co.kr.bemyplan.data.local.BeMyPlanDataStore
import co.kr.bemyplan.databinding.FragmentOnboarding1Binding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class OnboardingFragment1 : Fragment() {
    private var _binding: FragmentOnboarding1Binding? = null
    private val binding get() = _binding ?: error("Binding이 초기화 되지 않았습니다.")

    @Inject
    lateinit var dataStore: BeMyPlanDataStore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOnboarding1Binding.inflate(layoutInflater)
        val display = activity?.applicationContext?.resources?.displayMetrics
        val deviceWidth = display?.widthPixels
        val deviceHeight = display?.heightPixels
        val ivWidthRatio: Double = 230 / 360.0
        val ivHeightRatio: Double = 498 / 760.0
        val pageWidth = ivWidthRatio * deviceWidth!!
        val pageHeight = ivHeightRatio * deviceHeight!!
        binding.ivOnboarding.layoutParams.width = pageWidth.toInt()
        binding.ivOnboarding.layoutParams.height = pageHeight.toInt()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun initView() {
        binding.tvNext.setOnClickListener {
            (requireActivity() as OnboardingActivity).next()
        }
        binding.tvPass.setOnClickListener {
            dataStore.onBoarding = true
            (requireActivity() as OnboardingActivity).checkAutoLogin()
        }
    }
}