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

        var num = 0
        binding.tvKakaopay.setOnClickListener{
            binding.payment = 1
        }
        binding.tvToss.setOnClickListener{
            binding.payment = 2
        }
        binding.tvNaverpay.setOnClickListener{
            binding.payment=3
        }


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

        binding.ivBackBtn.setOnClickListener {
            activity?.supportFragmentManager
                ?.beginTransaction()
                ?.remove(this)
                ?.commit()
        }

        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}