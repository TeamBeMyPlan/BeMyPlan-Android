package co.kr.bemyplan.ui.list

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import co.kr.bemyplan.R
import co.kr.bemyplan.data.list.ContentModel
import co.kr.bemyplan.databinding.ActivityListBinding
import co.kr.bemyplan.ui.sort.SortFragment
import co.kr.bemyplan.ui.list.adapter.ListAdapter

class ListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityListBinding
    private lateinit var listAdapter: ListAdapter
    private var listItem = listOf<ContentModel>()
    private var orderBy: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_list)

        initList()
        initRecyclerView()
        clickBack()
        openBottomSheetDialog()
    }

    private fun initList() {
        listItem = listOf(
            ContentModel(R.drawable.rectangle_5715, "비마플", "한승현은 퇴근하고싶다", true, true),
            ContentModel(R.drawable.rectangle_5715, "비마플", "한승현은 퇴근하고싶다", true, false),
            ContentModel(R.drawable.rectangle_5715, "한승현", "한승현은 퇴근하고싶다", false, true),
            ContentModel(R.drawable.rectangle_5715, "한승현", "한승현은 퇴근하고싶다", false, false),
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

    // 임시코드, 추후 서버와 논의 후 ViewModel을 통한 데이터 전달로 바꿀 예정
    private fun openBottomSheetDialog() {
        binding.ivOrder.setOnClickListener {
            val bottomSheetDialogFragment = SortFragment {
                orderBy = it
                Log.d("mlog: orderBy", orderBy.toString())
            }
            bottomSheetDialogFragment.show(supportFragmentManager, bottomSheetDialogFragment.tag)
        }
    }
}