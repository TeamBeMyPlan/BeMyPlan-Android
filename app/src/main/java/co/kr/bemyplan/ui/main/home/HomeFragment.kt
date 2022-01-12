package co.kr.bemyplan.ui.main.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import co.kr.bemyplan.R
import co.kr.bemyplan.data.home.TempHomeData
import co.kr.bemyplan.databinding.FragmentHomeBinding
import co.kr.bemyplan.ui.list.ListActivity
import co.kr.bemyplan.ui.main.home.HomeAdapter
import co.kr.bemyplan.ui.main.home.HomeViewPagerAdapter
import co.kr.bemyplan.ui.purchase.before.BeforePurchaseActivity
import co.kr.bemyplan.util.ZoomOutPageTransformer

class HomeFragment : Fragment() {

    private lateinit var homeViewPagerAdapter: HomeViewPagerAdapter
    private lateinit var homeAdapter: HomeAdapter
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding?:error("Binding이 초기화 되지 않았습니다.")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(layoutInflater)
        initAdapterRecent()
        initAdapterEditor()
        initAdapterPopular()
        clickMore()

        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun initAdapterRecent(){
        homeAdapter = HomeAdapter {
            val intent = Intent(requireContext(), BeforePurchaseActivity::class.java)
            startActivity(intent)
        }
        binding.rvRecent.adapter=homeAdapter
        homeAdapter.planList.addAll(
            listOf(
                TempHomeData(R.drawable.img_home_recent, "27년 제주 토박이의 히든 플레이스 투어", ""),
                TempHomeData(R.drawable.img_home_recent, "27년 제주 토박이의 히든 플레이스 투어", ""),
                TempHomeData(R.drawable.img_home_recent, "27년 제주 토박이의 히든 플레이스 투어", ""),
                TempHomeData(R.drawable.img_home_recent, "27년 제주 토박이의 히든 플레이스 투어", ""),
                TempHomeData(R.drawable.img_home_recent, "27년 제주 토박이의 히든 플레이스 투어", "")
            )
        )
        homeAdapter.notifyDataSetChanged()
    }

    private fun initAdapterEditor(){
        homeAdapter = HomeAdapter {
            val intent = Intent(requireContext(), BeforePurchaseActivity::class.java)
            startActivity(intent)
        }
        binding.rvEditorSuggest.adapter=homeAdapter
        homeAdapter.planList.addAll(
            listOf(
                TempHomeData(R.drawable.img_home_editor, "푸드파이터들을 위한 찐먹킷리스트 투어", ""),
                TempHomeData(R.drawable.img_home_editor, "푸드파이터들을 위한 찐먹킷리스트 투어", ""),
                TempHomeData(R.drawable.img_home_editor, "푸드파이터들을 위한 찐먹킷리스트 투어", ""),
                TempHomeData(R.drawable.img_home_editor, "푸드파이터들을 위한 찐먹킷리스트 투어", ""),
                TempHomeData(R.drawable.img_home_editor, "푸드파이터들을 위한 찐먹킷리스트 투어", "")
            )
        )
        homeAdapter.notifyDataSetChanged()
    }

    private fun initAdapterPopular(){
        homeViewPagerAdapter = HomeViewPagerAdapter {
            val intent = Intent(requireContext(), BeforePurchaseActivity::class.java)
            startActivity(intent)
        }

        with(binding.vpPopular){
            adapter=homeViewPagerAdapter

            val pageMargin = resources.getDimensionPixelOffset(R.dimen.padding_24)
            val innerMargin = resources.getDimensionPixelOffset(R.dimen.padding_20)

            getChildAt(0).overScrollMode=RecyclerView.OVER_SCROLL_NEVER //맨 위에서 더 이상 위로 스크롤할 영역이 없을 때 위로 땡겨지지 않도록
            offscreenPageLimit=1 //리사이클러뷰에서 현재 보고있는 아이템의 양쪽으로 지정한 숫자만큼의 아이템을 유지한다. 그 밖의 아이템들은 필요할 때 어댑터에서 만든다.
            // Set the number of pages that should be retained to either side of the currently visible page(s). Pages beyond this limit will be recreated from the adapter when needed

            setPadding(pageMargin, 0, pageMargin, 0) //패딩 값 코드단에서 주기
            setPageTransformer(CompositePageTransformer().apply{
                addTransformer(ZoomOutPageTransformer())
                addTransformer{page, position-> page.translationX = position*-(innerMargin / 2) }
            })
        }

        homeViewPagerAdapter.planList.addAll(
            listOf(
                TempHomeData(R.drawable.img_home_popular, "인기 여행 일정", "제주도 & 우도 인생샷 투어"),
                TempHomeData(R.drawable.img_home_popular, "인기 여행 일정", "제주도 & 우도 인생샷 투어"),
                TempHomeData(R.drawable.img_home_popular, "인기 여행 일정", "제주도 & 우도 인생샷 투어"),
                TempHomeData(R.drawable.img_home_popular, "인기 여행 일정", "제주도 & 우도 인생샷 투어"),
                TempHomeData(R.drawable.img_home_popular, "인기 여행 일정", "제주도 & 우도 인생샷 투어")
            )
        )
        homeViewPagerAdapter.notifyDataSetChanged()
    }

    private fun clickMore() {
        val intent = Intent(requireContext(), ListActivity::class.java)
        binding.ivRecentMore.setOnClickListener {
            startActivity(intent)
        }
        binding.ivEditorMore.setOnClickListener {
            startActivity(intent)
        }
    }
}