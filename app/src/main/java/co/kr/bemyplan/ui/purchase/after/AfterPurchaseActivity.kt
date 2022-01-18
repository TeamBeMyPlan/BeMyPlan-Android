package co.kr.bemyplan.ui.purchase.after

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.NestedScrollView
import androidx.databinding.DataBindingUtil
import co.kr.bemyplan.R
import co.kr.bemyplan.data.entity.purchase.after.DailyContents
import co.kr.bemyplan.data.entity.purchase.after.Post
import co.kr.bemyplan.databinding.ActivityAfterPurchaseBinding
import co.kr.bemyplan.databinding.ItemDayButtonBinding
import co.kr.bemyplan.ui.list.ListActivity
import com.google.android.material.chip.ChipGroup
import net.daum.mf.map.api.MapView

class AfterPurchaseActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAfterPurchaseBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_after_purchase)

        // data 객체 생성
        binding.post = Post("kym", "여행 포스트 제목", listOf(),3)

        // fragment 보이기
        initFragment()
        // user button
        initUserButton()
        // back button
        initBackButton()
        // 일차별 버튼
        initChips(binding.post.spots)
        // 스크롤뷰 설정
        initNestedScrollView()

        setContentView(binding.root)

        // TODO: Kakao Map
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

    private fun initUserButton() {
        binding.clWriter.setOnClickListener {
            val intent = Intent(this, ListActivity::class.java)
            intent.putExtra("from", "user")
            startActivity(intent)
        }
    }

    private fun initBackButton() {
        binding.ivBack.setOnClickListener {
            finish()
        }
    }

    @SuppressLint("ResourceType")
    private fun initChips(data: List<DailyContents>) {
        val chipGroup: ChipGroup = binding.chipGroupDay
        for (i in data.indices) {
            val chip = ItemDayButtonBinding.inflate(layoutInflater)
            chip.root.id = View.generateViewId()
            chip.dailyContents = data[i]

            if(i == 0) chip.tvDayButton.isChecked = true
            chipGroup.addView(chip.root)
        }
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