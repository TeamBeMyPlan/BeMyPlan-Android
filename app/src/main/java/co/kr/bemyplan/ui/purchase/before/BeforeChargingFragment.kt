package co.kr.bemyplan.ui.purchase.before

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.NestedScrollView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import co.kr.bemyplan.R
import co.kr.bemyplan.data.purchase.before.ContentModel
import co.kr.bemyplan.data.purchase.before.SummaryModel
import co.kr.bemyplan.databinding.FragmentBeforeChargingBinding
import co.kr.bemyplan.ui.purchase.before.adapter.ContentAdapter
import co.kr.bemyplan.ui.purchase.before.adapter.SummaryAdapter

class BeforeChargingFragment : Fragment() {

    private var _binding: FragmentBeforeChargingBinding? = null
    private val binding get() = _binding ?: error("Binding이 초기화 되지 않았습니다.")
    private lateinit var summaryAdapter: SummaryAdapter
//    private lateinit var contentViewPagerAdapter: ContentViewPagerAdapter
    private lateinit var contentAdapter: ContentAdapter
    private var summaryItemList = listOf<SummaryModel>()
    private var contentItemList = listOf<ContentModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_before_charging, container, false)

        initList()
        initRecyclerView()
        initNestedScrollView()
        clickBack()
        clickScrap()
        clickPurchase()

        return binding.root
    }

    private fun initList() {
        summaryItemList = listOf(
            SummaryModel(R.drawable.icn_theme, "힐링"),
            SummaryModel(R.drawable.icn_place, "14곳"),
            SummaryModel(R.drawable.icn_restarurant, "3곳"),
            SummaryModel(R.drawable.icn_day, "4일"),
            SummaryModel(R.drawable.icn_friend, "친구"),
            SummaryModel(R.drawable.icn_money, "45만원"),
            SummaryModel(R.drawable.icn_transfortaion, "택시"),
            SummaryModel(R.drawable.icn_calendar, "8월"),
        )

        contentItemList = listOf(
            ContentModel(
                R.drawable.rectangle_5715,
                "그래서 난 눈누난나 ~ 그래서 난 눈누난나 ~ 그래서 난 눈누난나 ~ 그래서 난 눈누난나 ~ 그래서 난 눈누난나 ~ 그래서 난 눈누난나 ~ 그래서 난 눈누난나 ~ 그래서 난 눈누난나 ~ 그래서 난 눈누난나 ~ 그래서 난 눈누난나 ~ 그래서 난 눈누난나 ~ 그래서 난 눈누난나 ~ 그래서 난 눈누난나 ~ 그래서 난 눈누난나 ~ 그래서 난 눈누난나 ~ 그래서 난 눈누난나 ~ 그래서 난 눈누난나 ~ 그래서 난 눈누난나 ~ 그래서 난 눈누난나 ~ 그래서 난 눈누난나 ~ "
            ),
            ContentModel(R.drawable.rectangle_5715, "그래서 난 눈누난나 ~"),
            ContentModel(R.drawable.rectangle_5715, "그래서 난 눈누난나 ~"),
            ContentModel(R.drawable.rectangle_5715, "그래서 난 눈누난나 ~"),
        )
    }

    private fun initRecyclerView() {
        // Overview
        summaryAdapter = SummaryAdapter()
        summaryAdapter.itemList = summaryItemList
        binding.rvSummary.adapter = summaryAdapter

        // Preview
        // 가로 스크롤(뷰페이저)
//        contentViewPagerAdapter = ContentViewPagerAdapter()
//        contentViewPagerAdapter.itemList = contentItemList
//        binding.vpContent.adapter = contentViewPagerAdapter
//        binding.vpContent.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
//            override fun onPageSelected(position: Int) {
//                super.onPageSelected(position)
//                val view = (binding.vpContent[0] as RecyclerView).layoutManager?.findViewByPosition(position)
//
//                view?.post {
//                    val wMeasureSpec = View.MeasureSpec.makeMeasureSpec(view.width, View.MeasureSpec.EXACTLY)
//                    val hMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
//                    view.measure(wMeasureSpec, hMeasureSpec)
//
//                    if (binding.vpContent.layoutParams.height != view.measuredHeight) {
//                        binding.vpContent.layoutParams = (binding.vpContent.layoutParams).also { lp -> lp.height = view.measuredHeight }
//                    }
//                }
//            }
//        })
        // 세로 스크롤(리사이클러뷰)
        contentAdapter = ContentAdapter()
        contentAdapter.itemList = contentItemList
        binding.rvContent.adapter = contentAdapter
    }

    private fun initNestedScrollView() {
        var isVisible = true
        binding.nsv.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            if (scrollY < oldScrollY) {
                // 하단 View 표시하기
                if (!isVisible) {
                    isVisible = true
                    showLayoutPurchase()
                }
                // 타이틀 상단에 걸기
                setTopTitle()
            } else if (scrollY > oldScrollY) {
                if (scrollY == v.getChildAt(0).measuredHeight - v.measuredHeight) {
                    // 하단 View 보이기
                    isVisible = true
                    showLayoutPurchase()
                } else {
                    // 하단 View 숨기기
                    if (isVisible) {
                        isVisible = false
                        hideLayoutPurchase()
                    }
                }
                // 타이틀 상단에 걸기
                setTopTitle()
            }
        })
    }

    private fun showLayoutPurchase() {
        val layout = binding.layoutPurchase
        layout.animate()
            .translationY(0f)
            .alpha(1f)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationStart(animation: Animator?) {
                    super.onAnimationStart(animation)
                    layout.visibility = View.VISIBLE
                }
            })
    }

    private fun hideLayoutPurchase() {
        val layout = binding.layoutPurchase
        layout.animate()
            .translationY(layout.height.toFloat())
            .alpha(0f)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    super.onAnimationEnd(animation)
                    binding.layoutPurchase.visibility = View.GONE
                }
            })
    }

    private fun setTopTitle() {
        val rect = Rect()
        binding.nsv.getHitRect(rect)
        if (binding.tvTitle.getLocalVisibleRect(rect)) {
            // view가 보이는 경우
            binding.tvTopTitle.visibility = View.INVISIBLE
        } else {
            // view가 안 보이는 경우
            binding.tvTopTitle.visibility = View.VISIBLE
        }
    }

    private fun clickBack() {
        binding.layoutBack.setOnClickListener {
            requireActivity().finish()
        }
    }

    private fun clickScrap() {
        binding.layoutScrap.setOnClickListener {
            binding.ivScrap.apply {
                isSelected = !isSelected
            }
        }
    }

    private fun clickPurchase() {
        binding.layoutPurchase.setOnClickListener {
            val transaction = parentFragmentManager.beginTransaction()
            transaction.add(R.id.fragment_container_charging, ChargingFragment())
                .addToBackStack(null)
                .commit()
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}