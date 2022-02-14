package co.kr.bemyplan.ui.purchase.after

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import co.kr.bemyplan.data.entity.purchase.after.Spot
import co.kr.bemyplan.databinding.FragmentDailyContentsBinding
import co.kr.bemyplan.ui.purchase.after.adapter.DailyContentsAdapter
import co.kr.bemyplan.ui.purchase.after.viewmodel.AfterPurchaseViewModel

class DailyContentsFragment : Fragment() {
    private lateinit var contentsAdapter: DailyContentsAdapter
    private lateinit var routeAdapter: DailyContentsAdapter
    private var _binding: FragmentDailyContentsBinding? = null
    private val binding get() = _binding ?: error("Binding이 초기화 되지 않았습니다.")

    private lateinit var viewModel: AfterPurchaseViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDailyContentsBinding.inflate(layoutInflater, container, false)

        // viewmodel 설정
        viewModel = ViewModelProvider(requireActivity())[AfterPurchaseViewModel::class.java]
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

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
        routeAdapter = DailyContentsAdapter(DailyContentsAdapter.TYPE_ROUTE)

        viewModel.dailySpots.observe(viewLifecycleOwner) {
            contentsAdapter.setItems(it)

            if (it.size > 5) {
                routeAdapter.setItems(it.subList(0, 5))
                initMoreBtn(it)
                binding.clLookClose.setOnClickListener { _ -> initCloseBtn(it) }
                initCloseBtn(it)
            }
            else {
                routeAdapter.setItems(it)
                binding.clLookMore.isVisible = false
                binding.clLookClose.isVisible = false
            }
        }

        binding.rvDailyContents.adapter = contentsAdapter

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
        routeAdapter.setItems(items.subList(0, 5))
        binding.clLookMore.isVisible = true
        binding.clLookClose.isVisible = false
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}