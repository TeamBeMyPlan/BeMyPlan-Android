package co.kr.bemyplan.ui.purchase.after

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.graphics.Rect
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.widget.NestedScrollView
import androidx.databinding.DataBindingUtil
import co.kr.bemyplan.R
import co.kr.bemyplan.data.DailyContents
import co.kr.bemyplan.data.Post
import co.kr.bemyplan.databinding.ActivityAfterPurchaseBinding
import co.kr.bemyplan.ui.purchase.after.adapter.DailyContentsAdapter
import co.kr.bemyplan.ui.purchase.after.adapter.DayAdapter
import com.google.android.material.chip.ChipGroup
import net.daum.mf.map.api.MapView

class AfterPurchaseActivity : AppCompatActivity() {
    private lateinit var dayAdapter: DayAdapter
    private lateinit var binding: ActivityAfterPurchaseBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_after_purchase)

        // data 객체 생성
        binding.post = Post("감성을 느낄 수 있는 힐링여행", "thisisuzzwon", 4)

        // fragment 보이기
        initFragment()
        // back button
        initBackButton()
        // 일차별 버튼
        initDayButtonAdapter()
        // 스크롤뷰 설정
        initNestedScrollView()

        setContentView(binding.root)

        setMap()
    }

    private fun setMap() {
        val mapView = MapView(this)
        binding.mapView.addView(mapView)
    }

    private fun initFragment() {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        val fragment = DailyContentsFragment()
        fragmentTransaction.add(R.id.fcv_daily_context, fragment)
        fragmentTransaction.commit()
    }

    private fun initBackButton() {
        binding.ivBack.setOnClickListener {
            finish()
        }
    }

    private fun initDayButtonAdapter() {
        dayAdapter = DayAdapter()

        val items = listOf(
            DailyContents(1, false),
            DailyContents(2, false),
            DailyContents(3, false),
            DailyContents(4, false),
            DailyContents(5, false),
            DailyContents(6, true)
        )

        dayAdapter.setItems(items)
        binding.rvDayButton.adapter = dayAdapter
    }

    private fun initNestedScrollView() {
        binding.svDailyContents.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { _, _, _, _, _ ->
            setTopTitle()
        })
    }

    private fun setTopTitle() {
        val rect = Rect()
        binding.svDailyContents.getHitRect(rect)
        if (binding.tvTitle.getLocalVisibleRect(rect)) {
            // view가 보이는 경우
            binding.tvTopTitle.visibility = View.INVISIBLE
        } else {
            // view가 안 보이는 경우
            binding.tvTopTitle.visibility = View.VISIBLE
        }
    }
}