package co.kr.bemyplan.ui.purchase.before

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import co.kr.bemyplan.R
import co.kr.bemyplan.data.purchase.before.OverviewModel
import co.kr.bemyplan.data.purchase.before.PreviewModel
import co.kr.bemyplan.databinding.ActivityBeforePurchaseBinding
import co.kr.bemyplan.ui.purchase.before.adapter.OverviewAdapter
import co.kr.bemyplan.ui.purchase.before.adapter.PreviewAdapter

class BeforePurchaseActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBeforePurchaseBinding
    private lateinit var overviewAdapter: OverviewAdapter
    private lateinit var previewAdapter: PreviewAdapter
    private var overviewItemList = listOf<OverviewModel>()
    private var previewItemList = listOf<PreviewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_before_purchase)

        initList()
        initRecyclerView()
    }

    private fun initList() {
        overviewItemList = listOf(
            OverviewModel(R.drawable.icn_theme, "힐링"),
            OverviewModel(R.drawable.icn_place, "14곳"),
            OverviewModel(R.drawable.icn_restarurant, "3곳"),
            OverviewModel(R.drawable.icn_day, "4일"),
            OverviewModel(R.drawable.icn_friend, "친구"),
            OverviewModel(R.drawable.icn_money, "45만원"),
            OverviewModel(R.drawable.icn_transfortaion, "택시"),
            OverviewModel(R.drawable.icn_calendar, "8월"),
        )

        previewItemList = listOf(
            PreviewModel(R.drawable.prof, "안녕하세요 한승현입니다."),
            PreviewModel(R.drawable.prof, "안녕하세요 한승현입니다."),
            PreviewModel(R.drawable.prof, "안녕하세요 한승현입니다."),
            PreviewModel(R.drawable.prof, "안녕하세요 한승현입니다."),
        )
    }

    private fun initRecyclerView() {
        // Overview
        overviewAdapter = OverviewAdapter()
        overviewAdapter.itemList = overviewItemList
        binding.rvOverview.layoutManager = GridLayoutManager(this, 4)
        binding.rvOverview.adapter = overviewAdapter

        // Preview
        previewAdapter = PreviewAdapter()
        previewAdapter.itemList = previewItemList
        binding.rvPreview.layoutManager = LinearLayoutManager(this)
        binding.rvPreview.adapter = previewAdapter
    }
}