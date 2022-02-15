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

    // 어댑터 연결
    private fun initAdapter() {
        contentsAdapter = DailyContentsAdapter(DailyContentsAdapter.TYPE_CONTENTS, photoUrl = { photoUrl: String ->
            val intent = Intent(requireContext(), ImageViewActivity::class.java)
            intent.putExtra("photoUrl", photoUrl)
            requireActivity().startActivity(intent)
        })
        routeAdapter = DailyContentsAdapter(DailyContentsAdapter.TYPE_ROUTE)

        // 뷰모델에서 데이터 받아오기
        viewModel.dailySpots.observe(viewLifecycleOwner) {
            contentsAdapter.submitList(it)

            if (it.size > 5) { // 장소들이 5개 초과인 경우에 더보기, 닫기 버튼 추가
                routeAdapter.submitList(it.subList(0, 5))
                initMoreBtn(it)
                binding.clLookMore.setOnClickListener { _ -> initMoreBtn(it) }
                binding.clLookClose.setOnClickListener { _ -> initCloseBtn(it) }
            }
            else { // 장소들이 5개 이하면 버튼 없이 진행
                routeAdapter.submitList(it)
                binding.clLookMore.isVisible = false
                binding.clLookClose.isVisible = false
            }
        }

        binding.rvDailyContents.adapter = contentsAdapter
        binding.rvDailyRoute.adapter = routeAdapter
    }

    // 더보기 버튼
    private fun initMoreBtn(items: List<Spot>) {
        routeAdapter.submitList(items)
        binding.clLookMore.isVisible = false
        binding.clLookClose.isVisible = true
    }

    // 더보기 닫기 버튼
    private fun initCloseBtn(items: List<Spot>) {
        routeAdapter.submitList(items.subList(0, 5))
        binding.clLookMore.isVisible = true
        binding.clLookClose.isVisible = false
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}