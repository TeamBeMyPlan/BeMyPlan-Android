package co.kr.bemyplan.ui.purchase.before

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import co.kr.bemyplan.R
import co.kr.bemyplan.data.local.FirebaseDefaultEventParameters
import co.kr.bemyplan.databinding.FragmentChargingBinding
import co.kr.bemyplan.ui.purchase.before.viewmodel.BeforeChargingViewModel
import co.kr.bemyplan.util.CustomDialog
import co.kr.bemyplan.util.ToastMessage.shortToast
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase

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
        observeLiveData()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun clickKakaoPay() {
        binding.tvKakaopay.setOnClickListener {
            viewModel.selectPay(BeforeChargingViewModel.Pay.KAKAO)
        }
    }

    private fun clickToss() {
        binding.tvToss.setOnClickListener {
            viewModel.selectPay(BeforeChargingViewModel.Pay.TOSS)
        }
    }

    private fun clickNaverPay() {
        binding.tvNaverpay.setOnClickListener {
            viewModel.selectPay(BeforeChargingViewModel.Pay.NAVER)
        }
    }

    private fun clickPay() {
        val dialog = CustomDialog(requireContext(), "", "")

        binding.tvPayBtn.setOnClickListener {
            val fb = Firebase.analytics.apply {
                setDefaultEventParameters(FirebaseDefaultEventParameters.parameters)
            }
            fb.logEvent("clickPaymentButton", Bundle().apply {
                putInt("postIdx", viewModel.planId)
            })

            dialog.showConfirmDialog(R.layout.dialog_yes_zero_event)
            dialog.setOnClickedListener(object : CustomDialog.ButtonClickListener {
                override fun onClicked(num: Int) {
                    if (num == 1) {
                        viewModel.purchasePlan()
                    }
                }
            })
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

    private fun observeLiveData() {
        viewModel.purchaseSuccess.observe(viewLifecycleOwner) {
            when (it) {
                true -> {
                    val transaction = parentFragmentManager.beginTransaction()
                    val beforeChargingFragment = BeforeChargingFragment()
                    val chargedFragment = ChargedFragment()
                    transaction.replace(R.id.fragment_container_charging, chargedFragment)
                    transaction.remove(beforeChargingFragment)
                    transaction.commit()
                }
                false -> {
                    requireContext().shortToast("문제가 발생했습니다. 다시 시도해주세요.")
                }
            }
        }
    }
}