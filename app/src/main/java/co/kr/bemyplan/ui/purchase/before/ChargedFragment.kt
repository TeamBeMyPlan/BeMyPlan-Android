package co.kr.bemyplan.ui.purchase.before

import android.content.Intent
import android.os.Bundle
import android.util.Log
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

        val intent = Intent(activity, AfterPurchaseActivity::class.java)

        binding.ivClearBtn.setOnClickListener {
            requireActivity().finish()
        }
        binding.tvGotoContentBtn.setOnClickListener {
            startActivity(intent)
            intent.putExtra("postId", viewModel.postId)
            requireActivity().finish()
        }


        binding.tvContentTitle.post(Runnable {

            val lineCount: Int = binding.tvContentTitle.lineCount
            Log.d("yongmin", "$lineCount")
            Log.d("yongmin", binding.tvContentTitle.text.toString())
        })

        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}