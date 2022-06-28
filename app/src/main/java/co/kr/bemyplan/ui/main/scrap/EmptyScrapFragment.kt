package co.kr.bemyplan.ui.main.scrap

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import co.kr.bemyplan.R
import co.kr.bemyplan.databinding.FragmentEmptyScrapBinding
import co.kr.bemyplan.ui.main.scrap.adapter.ScrapRecommendAdapter
import co.kr.bemyplan.ui.main.scrap.viewmodel.ScrapViewModel
import co.kr.bemyplan.ui.purchase.after.AfterPurchaseActivity
import co.kr.bemyplan.ui.purchase.before.PurchaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EmptyScrapFragment : Fragment() {
    private var _binding: FragmentEmptyScrapBinding? = null
    private val binding get() = _binding ?: error("binding not initialized")
    private val viewModel by viewModels<ScrapViewModel>()
    private lateinit var scrapRecommendAdapter: ScrapRecommendAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding =
            DataBindingUtil.inflate(layoutInflater, R.layout.fragment_empty_scrap, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initRecyclerView()
        observeData()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun initView() {
        viewModel.getEmptyScrapList()
    }

    private fun initRecyclerView() {
        scrapRecommendAdapter = ScrapRecommendAdapter({
            viewModel.checkPurchased(it.planId)
            observeDataForStartActivity(
                it.planId,
                it.user.nickname,
                it.user.userId,
                it.thumbnailUrl
            )
        }, { planId, scrapStatus ->
            when (scrapStatus) {
                true -> viewModel.deleteScrap(planId)
                false -> viewModel.postScrap(planId)
            }
        })
        binding.rvRecommend.adapter = scrapRecommendAdapter
    }

    private fun observeData() {
        viewModel.suggestList.observe(viewLifecycleOwner) {
            scrapRecommendAdapter.replaceItem(it)
        }
    }

    private fun observeDataForStartActivity(
        planId: Int,
        authorNickname: String,
        authorUserId: Int,
        thumbnail: String
    ) {
        viewModel.firebaseAnalyticsProvider.firebaseAnalytics.logEvent(
            "clickTravelPlan",
            Bundle().apply {
                putString("source", "스크랩")
                putInt("planId", planId)
            })
        viewModel.isPurchased.observe(viewLifecycleOwner) {
            val intent = Intent(requireContext(), AfterPurchaseActivity::class.java).apply {
                putExtra("planId", planId)
                putExtra("authorNickname", authorNickname)
                putExtra("authorUserId", authorUserId)
            }
            startActivity(intent)
        }
        viewModel.isNotPurchased.observe(viewLifecycleOwner) {
            val intent = Intent(requireContext(), PurchaseActivity::class.java).apply {
                putExtra("planId", planId)
                putExtra("authorNickname", authorNickname)
                putExtra("authorUserId", authorUserId)
                putExtra("thumbnail", thumbnail)
            }
            startActivity(intent)
        }
    }
}