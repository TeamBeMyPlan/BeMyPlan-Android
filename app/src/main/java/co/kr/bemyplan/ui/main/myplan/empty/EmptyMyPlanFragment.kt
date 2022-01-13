package co.kr.bemyplan.ui.main.myplan.empty

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import co.kr.bemyplan.R
import co.kr.bemyplan.databinding.FragmentEmptyMyPlanBinding
import co.kr.bemyplan.ui.main.home.HomeFragment

class EmptyMyPlanFragment : Fragment() {
    private lateinit var binding: FragmentEmptyMyPlanBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEmptyMyPlanBinding.inflate(layoutInflater)
        btnEvent()
        return binding.root
    }

    private fun btnEvent() {
        binding.tvLookingAround.setOnClickListener {
            val fragmentHome = HomeFragment()
            val fragmentTransaction = parentFragment?.parentFragmentManager?.beginTransaction()
            fragmentTransaction?.replace(R.id.fragment_container_view_tag, fragmentHome)
            fragmentTransaction?.commit()
        }
    }
}