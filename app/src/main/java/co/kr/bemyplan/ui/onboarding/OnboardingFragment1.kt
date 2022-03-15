package co.kr.bemyplan.ui.onboarding

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import co.kr.bemyplan.R
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

        val ratio: Double = 213/360.0
        val pageWidth = ratio*deviceWidth!!
        val padding = ((deviceWidth-pageWidth)/2).toInt()
        binding.ivOnboarding.setPadding(padding, 0, padding, 0)

        binding.tvNext.setOnClickListener{
            OnboardingActivity().next()
            Log.d("onboardinglog", "클릭됨")
        }
        //혹은 데이터 바인딩으로
        //binding.onboarding = OnboardingActivity()
        //이러고 xml에서 android:onclick = @{onboarding::next}

        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}