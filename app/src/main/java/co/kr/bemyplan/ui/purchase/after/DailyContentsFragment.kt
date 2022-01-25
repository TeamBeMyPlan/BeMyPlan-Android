package co.kr.bemyplan.ui.purchase.after

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import co.kr.bemyplan.data.entity.purchase.after.Spot
import co.kr.bemyplan.databinding.FragmentDailyContentsBinding
import co.kr.bemyplan.ui.purchase.PurchaseActivity
import co.kr.bemyplan.ui.purchase.after.adapter.DailyContentsAdapter

class DailyContentsFragment(private val spotList: List<Spot>) : Fragment() {
    private lateinit var contentsAdapter: DailyContentsAdapter
    private lateinit var routeAdapter: DailyContentsAdapter
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

    @SuppressLint("NotifyDataSetChanged")
    private fun initAdapter() {
        contentsAdapter = DailyContentsAdapter(DailyContentsAdapter.TYPE_CONTENTS, photoUrl = { photoUrl: String ->
            val intent = Intent(requireContext(), ImageViewActivity::class.java)
            intent.putExtra("photoUrl", photoUrl)
            requireActivity().startActivity(intent)
        })
        routeAdapter = DailyContentsAdapter(DailyContentsAdapter.TYPE_ROUTE, photoUrl = { photoUrl: String ->
            val intent = Intent(requireContext(), ImageViewActivity::class.java)
            intent.putExtra("photoUrl", photoUrl)
            startActivity(intent)
        })

        contentsAdapter.setItems(spotList)
        binding.rvDailyContents.adapter = contentsAdapter

        if (spotList.size > 5) {
            routeAdapter.setItems(spotList.subList(0, 5))
            initMoreBtn(spotList)
            initCloseBtn(spotList)
        }
        else {
            routeAdapter.setItems(spotList)
            binding.clLookMore.isVisible = false
            binding.clLookClose.isVisible = false
        }

        binding.rvDailyRoute.adapter = routeAdapter
    }

    private fun initMoreBtn(items: List<Spot>) {
        binding.clLookMore.setOnClickListener {
            routeAdapter.setItems(items)
            binding.clLookMore.isVisible = false
            binding.clLookClose.isVisible = true
        }
    }

    private fun initCloseBtn(items: List<Spot>) {
        binding.clLookClose.setOnClickListener {
            routeAdapter.setItems(items.subList(0, 5))
            binding.clLookMore.isVisible = true
            binding.clLookClose.isVisible = false
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}