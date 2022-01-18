package co.kr.bemyplan.ui.list

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import co.kr.bemyplan.R
import co.kr.bemyplan.data.entity.list.ContentModel
import co.kr.bemyplan.databinding.ActivityListBinding
import co.kr.bemyplan.ui.list.adapter.ListAdapter
import co.kr.bemyplan.ui.list.viewmodel.ListViewModel
import co.kr.bemyplan.ui.purchase.PurchaseActivity

class ListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityListBinding
    private val viewModel by viewModels<ListViewModel>()
    private lateinit var listAdapter: ListAdapter
    private var listItem = listOf<ContentModel>()
    private var orderBy: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_list)
        val from = intent.getStringExtra("from")
        initList(from)
        initRecyclerView()
        clickBack()
    }

    private fun initList(from: String?) {
        when (from) {
            "new" -> {
                viewModel.getNewList(0, 10)
                viewModel.newList.observe(this) {
                    listItem = it
                    initRecyclerView()
                }
            }
            "suggest" -> {
                viewModel.getSuggestList(0, 10)
                viewModel.suggestList.observe(this) {
                    listItem = it
                    initRecyclerView()
                }
            }
            "location" -> {
                viewModel.getLocationList(2, 0, 10, "created_at")
                viewModel.locationList.observe(this) {
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