package co.kr.bemyplan.ui.list

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import co.kr.bemyplan.R
import co.kr.bemyplan.data.list.ContentModel
import co.kr.bemyplan.databinding.ActivityListBinding
import co.kr.bemyplan.ui.list.adapter.ListAdapter

class ListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityListBinding
    private lateinit var listAdapter: ListAdapter
    private var listItem = listOf<ContentModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_list)

        initList()
        initRecyclerView()
        clickBack()
    }

    private fun initList() {
        listItem = listOf(
            ContentModel(R.drawable.rectangle_5715, "한승현", "한승현은 퇴근하고싶다", true, true),
            ContentModel(R.drawable.prof, "한승현", "한승현은 퇴근하고싶다", true, false),
            ContentModel(R.drawable.prof, "한승현", "한승현은 퇴근하고싶다", false, true),
            ContentModel(R.drawable.prof, "한승현", "한승현은 퇴근하고싶다", false, false),
        )
    }

    private fun initRecyclerView() {
        listAdapter = ListAdapter()
        listAdapter.itemList = listItem
        binding.rvLinearContent.adapter = listAdapter
    }

    private fun clickBack() {
        binding.ivBack.setOnClickListener {
            finish()
        }
    }
}