package co.kr.bemyplan.ui.onboarding

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import co.kr.bemyplan.R
import co.kr.bemyplan.databinding.FragmentOnboarding2Binding
import co.kr.bemyplan.databinding.FragmentOnboarding3Binding
import co.kr.bemyplan.ui.main.MainActivity
import co.kr.bemyplan.util.ToastMessage.shortToast

class OnboardingFragment3 : Fragment() {
    private var _binding : FragmentOnboarding3Binding? = null
    private val binding get() = _binding ?: error("Binding이 초기화 되지 않았습니다.")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOnboarding3Binding.inflate(layoutInflater)
        val intent = Intent(activity, MainActivity::class.java)
        binding.layoutBottom.setOnClickListener{
            startActivity(intent)
        }
        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}