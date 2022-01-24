package co.kr.bemyplan.ui.purchase.before

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import co.kr.bemyplan.R
import co.kr.bemyplan.databinding.FragmentChargingBinding
import co.kr.bemyplan.ui.purchase.before.viewmodel.BeforeChargingViewModel

class ChargingFragment : Fragment() {

    private var _binding: FragmentChargingBinding? = null
    private val binding get() = _binding ?: error("Binding이 초기화 되지 않았습니다.")
    private val viewModel by activityViewModels<BeforeChargingViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_charging, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        clickKakaoPay()
        clickToss()
        clickNaverPay()
        clickPay()
        clickBack()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun clickKakaoPay() {
        binding.tvKakaopay.setOnClickListener{
            viewModel.selectPay(BeforeChargingViewModel.Pay.KAKAO)
        }
    }

    private fun clickToss() {
        binding.tvToss.setOnClickListener{
            viewModel.selectPay(BeforeChargingViewModel.Pay.TOSS)
        }
    }

    private fun clickNaverPay() {
        binding.tvNaverpay.setOnClickListener{
            viewModel.selectPay(BeforeChargingViewModel.Pay.NAVER)
        }
    }

    private fun clickPay() {
        binding.tvPayBtn.setOnClickListener {
            val chargedFragment = ChargedFragment()
            val transaction = parentFragmentManager.beginTransaction()
            val chargingFragment = ChargingFragment()
            val beforeChargingFragment = BeforeChargingFragment()
            transaction.replace(R.id.fragment_container_charging, chargedFragment)
            transaction.remove(beforeChargingFragment)
            transaction.remove(chargingFragment)
            transaction.commit()
        }
    }

    private fun clickBack() {
        binding.ivBackBtn.setOnClickListener {
            activity?.supportFragmentManager
                ?.beginTransaction()
                ?.remove(this)
                ?.commit()
        }
    }
}