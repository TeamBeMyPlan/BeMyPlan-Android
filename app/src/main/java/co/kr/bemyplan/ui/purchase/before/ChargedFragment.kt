package co.kr.bemyplan.ui.purchase.before

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import co.kr.bemyplan.databinding.FragmentChargedBinding
import co.kr.bemyplan.ui.purchase.after.AfterPurchaseActivity
import co.kr.bemyplan.ui.purchase.before.viewmodel.BeforeChargingViewModel

class ChargedFragment : Fragment() {

    private var _binding: FragmentChargedBinding? = null
    private val binding get() = _binding ?: error("Binding이 초기화 되지 않았습니다.")
    private val viewModel by activityViewModels<BeforeChargingViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentChargedBinding.inflate(layoutInflater)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
//        무슨 코드인지 몰라서 일단 주석처리
//        binding.tvContentTitle.post(Runnable {
//            val lineCount: Int = binding.tvContentTitle.lineCount
//            Log.d("yongmin", "$lineCount")
//            Log.d("yongmin", binding.tvContentTitle.text.toString())
//        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        clickExit()
        clickGoToContent()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun clickExit() {
        binding.ivClearBtn.setOnClickListener {
            requireActivity().finish()
        }
    }

    private fun clickGoToContent() {
        binding.tvGotoContentBtn.setOnClickListener {
            val intent = Intent(requireContext(), AfterPurchaseActivity::class.java).apply {
                putExtra("planId", viewModel.planId)
                putExtra("scrapStatus", viewModel.scrapStatus.value)
                putExtra("authorNickname", viewModel.authorNickname)
                putExtra("authorUserId", viewModel.authorUserId)
            }
            startActivity(intent)
            requireActivity().finish()
        }
    }
}