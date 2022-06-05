package co.kr.bemyplan.ui.main.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.*
import androidx.viewpager2.widget.CompositePageTransformer
import co.kr.bemyplan.databinding.FragmentHomeBinding
import co.kr.bemyplan.ui.list.ListActivity
import co.kr.bemyplan.ui.purchase.after.AfterPurchaseActivity
import co.kr.bemyplan.ui.purchase.before.PurchaseActivity
import co.kr.bemyplan.util.ZoomOutPageTransformer
import com.github.rubensousa.gravitysnaphelper.GravitySnapHelper
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var homeViewPagerAdapter: HomeViewPagerAdapter
    private lateinit var recentAdapter: HomeAdapter
    private lateinit var editorAdapter: HomeAdapter
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding ?: error("Binding이 초기화 되지 않았습니다.")
    private val homeViewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(layoutInflater)
        initAdapterNew()
        initAdapterSuggest()
        initAdapterPopular()
        clickMore()
        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun initAdapterNew() {
        homeViewModel.getNewData()

        recentAdapter = HomeAdapter({
            val intent = Intent(requireContext(), PurchaseActivity::class.java).apply {
                putExtra("planId", it.planId)
                putExtra("scrapStatus", it.scrapStatus)
                putExtra("authorNickname", it.user.nickname)
                putExtra("authorUserId", it.user.userId)
                putExtra("thumbnail", it.thumbnailUrl)
            }
            startActivity(intent)
        }, {
            val intent = Intent(requireContext(), AfterPurchaseActivity::class.java).apply {
                putExtra("planId", it.planId)
                putExtra("scrapStatus", it.scrapStatus)
                putExtra("authorNickname", it.user.nickname)
                putExtra("authorUserId", it.user.userId)
                putExtra("thumbnail", it.thumbnailUrl)
            }
            startActivity(intent)
        })
        binding.rvRecent.adapter = recentAdapter

        homeViewModel.new.observe(viewLifecycleOwner) {
            recentAdapter.planList.addAll(it)
            Log.d("yongminNewAdapter", it.toString())
            recentAdapter.notifyDataSetChanged()
        }
        recentAdapter.notifyDataSetChanged()
    }

    private fun initAdapterSuggest() {
        homeViewModel.getSuggestData()

        editorAdapter = HomeAdapter({
            val intent = Intent(requireContext(), PurchaseActivity::class.java).apply {
                putExtra("planId", it.planId)
                putExtra("scrapStatus", it.scrapStatus)
                putExtra("authorNickname", it.user.nickname)
                putExtra("authorUserId", it.user.userId)
                putExtra("thumbnail", it.thumbnailUrl)
            }
            startActivity(intent)
        }, {
            val intent = Intent(requireContext(), AfterPurchaseActivity::class.java).apply {
                putExtra("planId", it.planId)
                putExtra("scrapStatus", it.scrapStatus)
                putExtra("authorNickname", it.user.nickname)
                putExtra("authorUserId", it.user.userId)
                putExtra("thumbnail", it.thumbnailUrl)
            }
            startActivity(intent)
        })
        binding.rvEditorSuggest.adapter = editorAdapter

        homeViewModel.suggest.observe(viewLifecycleOwner) {
            editorAdapter.planList.addAll(it)
            Log.d("yongminSuggestAdapter", it.toString())
            editorAdapter.notifyDataSetChanged()
        }
        editorAdapter.notifyDataSetChanged()
    }

    private fun initAdapterPopular() {
        homeViewModel.getPopularData()

        homeViewPagerAdapter = HomeViewPagerAdapter({
            val intent = Intent(requireContext(), PurchaseActivity::class.java).apply {
                putExtra("planId", it.planId)
                putExtra("scrapStatus", it.scrapStatus)
                putExtra("authorNickname", it.user.nickname)
                putExtra("authorUserId", it.user.userId)
                putExtra("thumbnail", it.thumbnailUrl)
            }
            startActivity(intent)
        }, {
            val intent = Intent(requireContext(), AfterPurchaseActivity::class.java).apply {
                putExtra("planId", it.planId)
                putExtra("scrapStatus", it.scrapStatus)
                putExtra("authorNickname", it.user.nickname)
                putExtra("authorUserId", it.user.userId)
                putExtra("thumbnail", it.thumbnailUrl)
            }
            startActivity(intent)
        })

        homeViewModel.popular.observe(viewLifecycleOwner) {
            homeViewPagerAdapter.planList.addAll(it)
            Log.d("yongminAddLog", it.toString())
            homeViewPagerAdapter.notifyDataSetChanged()
        }

        with(binding.vpPopular) {
            adapter = homeViewPagerAdapter

            val display = activity?.applicationContext?.resources?.displayMetrics
            val deviceWidth = display?.widthPixels

            val ratio: Double = 312 / 360.0
            val pageWidth = ratio * deviceWidth!!
            val pagePadding = ((deviceWidth - pageWidth) / 2).toInt()
            val innerPadding = pagePadding / 2

            getChildAt(0).overScrollMode =
                RecyclerView.OVER_SCROLL_NEVER //맨 위에서 더 이상 위로 스크롤할 영역이 없을 때 위로 땡겨지지 않도록
            offscreenPageLimit =
                1 //리사이클러뷰에서 현재 보고있는 아이템의 양쪽으로 지정한 숫자만큼의 아이템을 유지한다. 그 밖의 아이템들은 필요할 때 어댑터에서 만든다.
            // Set the number of pages that should be retained to either side of the currently visible page(s). Pages beyond this limit will be recreated from the adapter when needed

            setPadding(pagePadding, 0, pagePadding, 0) //패딩 값 코드단에서 주기
            setPageTransformer(CompositePageTransformer().apply {
                addTransformer(ZoomOutPageTransformer())
                addTransformer { page, position -> page.translationX = position * -(innerPadding) }
            })
        }
        homeViewPagerAdapter.notifyDataSetChanged()
    }

    private fun clickMore() {
        val intent = Intent(requireContext(), ListActivity::class.java)
        binding.ivRecentMore.setOnClickListener {
            intent.putExtra("from", "new")
            startActivity(intent)
        }
        binding.ivEditorMore.setOnClickListener {
            intent.putExtra("from", "suggest")
            startActivity(intent)
        }

    }
}