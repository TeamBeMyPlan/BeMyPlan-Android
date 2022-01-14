package co.kr.bemyplan.ui.purchase.before

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import co.kr.bemyplan.R
import co.kr.bemyplan.databinding.FragmentBeforeChargingBinding
import co.kr.bemyplan.databinding.FragmentChargingBinding
import co.kr.bemyplan.databinding.FragmentHomeBinding

class ChargingFragment : Fragment() {

    private var _binding: FragmentChargingBinding? = null
    private val binding get() = _binding?:error("Binding이 초기화 되지 않았습니다.")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentChargingBinding.inflate(layoutInflater)

        binding.tvKakaopay.setOnClickListener{
            Log.d("yongminClick", "naverpay click")
        }
        binding.tvToss.setOnClickListener{

        }
        binding.tvNaverpay.setOnClickListener{}

//        if(binding.tvKakaopay.isPressed){
//            binding.tvKakaopay.isPressed = true
//            binding.tvNaverpay.isPressed = false
//            binding.tvToss.isPressed = false
//        }
//        else if(binding.tvNaverpay.isPressed){
//            binding.tvNaverpay.isPressed = true
//            binding.tvKakaopay.isPressed = false
//            binding.tvToss.isPressed = false
//        }
//        else if(binding.tvToss.isPressed){
//            binding.tvToss.isPressed = true
//            binding.tvNaverpay.isPressed = false
//            binding.tvKakaopay.isPressed = false
//        }

        if(binding.tvKakaopay.isSelected){
            binding.tvKakaopay.isSelected = true
            binding.tvNaverpay.isSelected = false
            binding.tvToss.isSelected = false
        }
        else if(binding.tvNaverpay.isSelected){
            binding.tvNaverpay.isSelected = true
            binding.tvKakaopay.isSelected = false
            binding.tvToss.isSelected = false
        }
        else if(binding.tvToss.isSelected){
            binding.tvToss.isSelected = true
            binding.tvNaverpay.isSelected = false
            binding.tvKakaopay.isSelected = false
        }


        binding.tvPayBtn.setOnClickListener{
            val chargedFragment = ChargedFragment()
            val transaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container_charging, chargedFragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }

        binding.ivBackBtn.setOnClickListener{
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