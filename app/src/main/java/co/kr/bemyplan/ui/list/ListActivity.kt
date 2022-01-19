package co.kr.bemyplan.ui.list

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import co.kr.bemyplan.R
import co.kr.bemyplan.data.entity.list.ContentModel
import co.kr.bemyplan.databinding.ActivityListBinding
import co.kr.bemyplan.ui.list.adapter.ListAdapter
import co.kr.bemyplan.ui.list.viewmodel.ListViewModel
import co.kr.bemyplan.ui.purchase.PurchaseActivity
import co.kr.bemyplan.ui.sort.SortFragment

class ListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityListBinding
    private val viewModel by viewModels<ListViewModel>()
    private lateinit var listAdapter: ListAdapter
    private var listItem = listOf<ContentModel>()
    var from: String = ""
    var areaId: Int = -1
    var userId: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_list)
        from = intent.getStringExtra("from") ?: ""
        areaId = intent.getIntExtra("area_id", 2)
        userId = intent.getStringExtra("userId") ?: "1"
        initList(from)
        initRecyclerView()
        clickBack()
    }

    private fun initList(from: String?) {
        when (from) {
            "new" -> {
                binding.layoutSort.visibility = View.GONE
                viewModel.getNewList()
                viewModel.newList.observe(this) {
                    listItem = it
                    initRecyclerView()
                }
            }
            "suggest" -> {
                binding.layoutSort.visibility = View.GONE
                viewModel.getSuggestList()
                viewModel.suggestList.observe(this) {
                    listItem = it
                    initRecyclerView()
                }
            }
            "location" -> {
                viewModel.getLocationList(areaId)
                viewModel.locationList.observe(this) {
                    listItem = it
                    initRecyclerView()
                }
            }
            "user" -> {
                viewModel.getUserPostList(userId)
                viewModel.userPostList.observe(this) {
                    listItem = it
                    initRecyclerView()
                }
            }
        }
    }

    private fun initRecyclerView() {
        listAdapter = ListAdapter {
            val intent = Intent(this, PurchaseActivity::class.java)
            startActivity(intent)
        }
        listAdapter.itemList = listItem
        binding.rvLinearContent.adapter = listAdapter
    }

    private fun clickBack() {
        binding.ivBack.setOnClickListener {
            finish()
        }
    }
}