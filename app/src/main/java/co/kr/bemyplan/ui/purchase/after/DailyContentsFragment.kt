package co.kr.bemyplan.ui.purchase.after

import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.browser.customtabs.CustomTabsClient.getPackageName
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import co.kr.bemyplan.databinding.FragmentDailyContentsBinding
import co.kr.bemyplan.domain.model.purchase.after.MergedPlanAndInfo
import co.kr.bemyplan.ui.purchase.after.adapter.DailyContentsAdapter
import co.kr.bemyplan.ui.purchase.after.viewmodel.AfterPurchaseViewModel
import dagger.hilt.android.AndroidEntryPoint
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapReverseGeoCoder
import timber.log.Timber
import java.util.*

@AndroidEntryPoint
class DailyContentsFragment : Fragment() {
    private lateinit var contentsAdapter: DailyContentsAdapter
    private lateinit var routeAdapter: DailyContentsAdapter
    private var _binding: FragmentDailyContentsBinding? = null
    private val binding get() = _binding ?: error("Binding이 초기화 되지 않았습니다.")

    private val viewModel: AfterPurchaseViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDailyContentsBinding.inflate(layoutInflater, container, false)

        // viewmodel 설정
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
        viewModel.mergedPlanAndInfo.observe(viewLifecycleOwner) {
            viewModel.setInfos(it.day - 1)
            contentsAdapter.submitList(it.infos)

            if (it.infos.size > 5) { // 장소들이 5개 초과인 경우에 더보기, 닫기 버튼 추가
                routeAdapter.submitList(it.infos.subList(0, 5))
                binding.clLookMore.setOnClickListener { _ -> initMoreBtn(it) }
                binding.clLookClose.setOnClickListener { _ -> initCloseBtn(it) }
            }
            else { // 장소들이 5개 이하면 버튼 없이 진행
                routeAdapter.submitList(it.infos)
                binding.clLookMore.isVisible = false
                binding.clLookClose.isVisible = false
            }
        }

        viewModel.addressNameList.observe(viewLifecycleOwner) {
            contentsAdapter.addressList.addAll(it)
            contentsAdapter.notifyDataSetChanged()
        }

        binding.rvDailyContents.adapter = contentsAdapter
        binding.rvDailyRoute.adapter = routeAdapter
    }

    // 더보기 버튼
    private fun initMoreBtn(items: MergedPlanAndInfo) {
        routeAdapter.submitList(items.infos)
        binding.clLookMore.isVisible = false
        binding.clLookClose.isVisible = true
    }

    // 더보기 닫기 버튼
    private fun initCloseBtn(items: MergedPlanAndInfo) {
        routeAdapter.submitList(items.infos.subList(0, 5))
        binding.clLookMore.isVisible = true
        binding.clLookClose.isVisible = false
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}