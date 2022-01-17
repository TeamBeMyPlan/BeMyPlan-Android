package co.kr.bemyplan.ui.purchase.before

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import co.kr.bemyplan.R
import co.kr.bemyplan.databinding.FragmentChargedBinding
import co.kr.bemyplan.databinding.FragmentChargingBinding
import co.kr.bemyplan.ui.purchase.after.AfterPurchaseActivity

class ChargedFragment : Fragment() {

    private var _binding: FragmentChargedBinding? = null
    private val binding get() = _binding?:error("Binding이 초기화 되지 않았습니다.")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentChargedBinding.inflate(layoutInflater)

        val intent = Intent(activity, AfterPurchaseActivity::class.java)

        binding.ivClearBtn.setOnClickListener{
            activity?.finish()
        }
        binding.tvGotoContentBtn.setOnClickListener{
            startActivity(intent)
            activity?.finish()
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