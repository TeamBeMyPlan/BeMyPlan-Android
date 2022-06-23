package co.kr.bemyplan.ui.main.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import co.kr.bemyplan.data.firebase.FirebaseAnalyticsProvider
import co.kr.bemyplan.data.local.BeMyPlanDataStore
import co.kr.bemyplan.databinding.FragmentHomeBinding
import co.kr.bemyplan.ui.list.ListActivity
import co.kr.bemyplan.ui.purchase.after.AfterPurchaseActivity
import co.kr.bemyplan.ui.purchase.before.PurchaseActivity
import co.kr.bemyplan.util.ZoomOutPageTransformer
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var homeViewPagerAdapter: HomeViewPagerAdapter
    private lateinit var recentAdapter: HomeAdapter
    private lateinit var editorAdapter: HomeAdapter
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding ?: error("Binding이 초기화 되지 않았습니다.")
    private val homeViewModel: HomeViewModel by viewModels()

    @Inject
    lateinit var beMyPlanDataStore: BeMyPlanDataStore

    @Inject
    lateinit var firebaseAnalyticsProvider: FirebaseAnalyticsProvider

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeViewModel.getNewData()
        homeViewModel.getPopularData()
        homeViewModel.getSuggestData()
        initView()
        observeData()
    }

    override fun onResume() {
        super.onResume()
        homeViewModel.isPurchased.removeObservers(viewLifecycleOwner)
        homeViewModel.isNotPurchased.removeObservers(viewLifecycleOwner)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun initView() {
        initAdapterNew()
        initAdapterSuggest()
        initAdapterPopular()
        clickMore()
    }

    private fun observeData() {
        homeViewModel.new.observe(viewLifecycleOwner) {
            recentAdapter.planList.addAll(it)
            recentAdapter.notifyDataSetChanged()
        }
        homeViewModel.suggest.observe(viewLifecycleOwner) {
            editorAdapter.planList.addAll(it)
            editorAdapter.notifyDataSetChanged()
        }
        homeViewModel.popular.observe(viewLifecycleOwner) {
            homeViewPagerAdapter.planList.addAll(it)
            homeViewPagerAdapter.notifyDataSetChanged()
        }
    }

    private fun initAdapterNew() {
        recentAdapter = HomeAdapter {
            if (beMyPlanDataStore.userId != 0) {
                homeViewModel.checkPurchased(it.planId)
                observeDataForStartActivity(
                    it.planId,
                    it.user.nickname,
                    it.user.userId,
                    it.thumbnailUrl
                )
            } else {
                val intent = Intent(requireContext(), PurchaseActivity::class.java)
                startActivity(intent)
            }
        }
        binding.rvRecent.adapter = recentAdapter
    }

    private fun initAdapterSuggest() {
        editorAdapter = HomeAdapter {
            if (beMyPlanDataStore.userId != 0) {
                homeViewModel.checkPurchased(it.planId)
                observeDataForStartActivity(
                    it.planId,
                    it.user.nickname,
                    it.user.userId,
                    it.thumbnailUrl
                )
            } else {
                val intent = Intent(requireContext(), PurchaseActivity::class.java)
                startActivity(intent)
            }
        }
        binding.rvEditorSuggest.adapter = editorAdapter
    }

    private fun initAdapterPopular() {
        homeViewPagerAdapter = HomeViewPagerAdapter {
            if (beMyPlanDataStore.userId != 0) {
                homeViewModel.checkPurchased(it.planId)
                observeDataForStartActivity(
                    it.planId,
                    it.user.nickname,
                    it.user.userId,
                    it.thumbnailUrl
                )
            } else {
                val intent = Intent(requireContext(), PurchaseActivity::class.java)
                startActivity(intent)
            }
        }

        with(binding.vpPopular) {
            adapter = homeViewPagerAdapter
            val display = activity?.applicationContext?.resources?.displayMetrics
            val deviceWidth = display?.widthPixels
            val ratio: Double = 312 / 360.0
            val pageWidth = ratio * deviceWidth!!
            val pagePadding = ((deviceWidth - pageWidth) / 2).toInt()
            val innerPadding = pagePadding / 2
            //맨 위에서 더 이상 위로 스크롤할 영역이 없을 때 위로 땡겨지지 않도록
            getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
            // 리사이클러뷰에서 현재 보고있는 아이템의 양쪽으로 지정한 숫자만큼의 아이템을 유지한다. 그 밖의 아이템들은 필요할 때 어댑터에서 만든다.
            // Set the number of pages that should be retained to either side of the currently visible page(s). Pages beyond this limit will be recreated from the adapter when needed
            offscreenPageLimit = 1
            setPadding(pagePadding, 0, pagePadding, 0) //패딩 값 코드단에서 주기
            setPageTransformer(CompositePageTransformer().apply {
                addTransformer(ZoomOutPageTransformer())
                addTransformer { page, position -> page.translationX = position * -(innerPadding) }
            })
        }
    }

    private fun clickMore() {
        val intent = Intent(requireContext(), ListActivity::class.java)
        binding.ivRecentMore.setOnClickListener {
            firebaseAnalyticsProvider.firebaseAnalytics.logEvent("clickHomeRecentPlanList", null)
            intent.putExtra("from", "new")
            startActivity(intent)
        }
        binding.ivEditorMore.setOnClickListener {
            firebaseAnalyticsProvider.firebaseAnalytics.logEvent("clickHomeRecommendPlanList", null)
            intent.putExtra("from", "suggest")
            startActivity(intent)
        }
    }

    private fun observeDataForStartActivity(
        planId: Int,
        authorNickname: String,
        authorUserId: Int,
        thumbnail: String
    ) {
        firebaseAnalyticsProvider.firebaseAnalytics.logEvent("clickTravelPlan", Bundle().apply {
            putString("source", "홈")
            putInt("planId", planId)
        })
        homeViewModel.isPurchased.observe(viewLifecycleOwner) {
            val intent = Intent(requireContext(), AfterPurchaseActivity::class.java).apply {
                putExtra("planId", planId)
                putExtra("authorNickname", authorNickname)
                putExtra("authorUserId", authorUserId)
            }
            startActivity(intent)
        }
        homeViewModel.isNotPurchased.observe(viewLifecycleOwner) {
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