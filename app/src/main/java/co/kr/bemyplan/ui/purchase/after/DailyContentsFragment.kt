package co.kr.bemyplan.ui.purchase.after

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import co.kr.bemyplan.databinding.FragmentDailyContentsBinding
import co.kr.bemyplan.ui.purchase.after.adapter.DailyContentsAdapter

class DailyContentsFragment : Fragment() {
    private lateinit var dailyContentsAdapter: DailyContentsAdapter
    private var _binding: FragmentDailyContentsBinding? = null
    private val binding get() = _binding ?: error("Binding이 초기화 되지 않았습니다.")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDailyContentsBinding.inflate(layoutInflater, container, false)
        initAdapter()

        return binding.root
    }

    private fun initAdapter() {
        dailyContentsAdapter = DailyContentsAdapter()
        binding.rvDailyContents.adapter = dailyContentsAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}