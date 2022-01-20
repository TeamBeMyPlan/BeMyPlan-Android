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
    var userId: Int = -1
    var authorNickname: String = ""
    var locationName: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_list)
        from = intent.getStringExtra("from") ?: ""
        areaId = intent.getIntExtra("areaId", -1)
        userId = intent.getIntExtra("userId", -1)
        locationName = intent.getStringExtra("locationName") ?: ""
        authorNickname = intent.getStringExtra("authorNickname") ?: ""
        Log.d("mlog: ListActivity.userId", userId.toString())
        Log.d("mlog: ListActivity.userId", authorNickname.toString())
//        authorId = intent.getStringExtra("authorId") ?: ""
        initList(from)
        initRecyclerView()
        clickBack()
        openBottomSheetDialog()
    }

    private fun initList(from: String?) {
        when (from) {
            "new" -> {
                Log.d("mlog: new", "success")
                binding.layoutSort.visibility = View.GONE
                viewModel.getNewList()
                binding.tvTitle.text = "최신 등록 여행 일정"
                viewModel.newList.observe(this) {
                    listItem = it
                    initRecyclerView()
                }
                viewModel.sort.observe(this) {
                    viewModel.getNewList()
                }
            }
            "suggest" -> {
                Log.d("mlog: suggest", "success")
                binding.layoutSort.visibility = View.GONE
                viewModel.getSuggestList()
                binding.tvTitle.text = "비마플 추천 여행 일정"
                viewModel.suggestList.observe(this) {
                    listItem = it
                    initRecyclerView()
                }
                viewModel.sort.observe(this) {
                    viewModel.getSuggestList()
                }
            }
            "location" -> {
                Log.d("mlog: location", "success")
                viewModel.getLocationList(areaId)
                binding.tvTitle.text = locationName
                viewModel.locationList.observe(this) {
                    listItem = it
                    initRecyclerView()
                }
                viewModel.sort.observe(this) {
                    viewModel.getLocationList(areaId)
                }
            }
            "user" -> {
                Log.d("mlog: user", "success")
                viewModel.getUserPostList(userId)
                binding.tvTitle.text = authorNickname
                viewModel.userPostList.observe(this) {
                    listItem = it
                    initRecyclerView()
                }
                viewModel.sort.observe(this) {
                    viewModel.getUserPostList(userId)
                }
            }
        }
    }

    private fun initRecyclerView() {
        listAdapter = ListAdapter {
            val intent = Intent(this, PurchaseActivity::class.java)
            // TODO: 분기처리 필요
            intent.putExtra("postId", it.id)
            intent.putExtra("nickname", it.author)
            Log.d("mlog: putExtra에서 postId", it.id.toString())
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

    private fun openBottomSheetDialog() {
        binding.ivOrder.setOnClickListener {
            val bottomSheetDialogFragment = SortFragment()
            bottomSheetDialogFragment.show(supportFragmentManager, bottomSheetDialogFragment.tag)
            Log.d("mlog: ListActivity에서의 sort", viewModel.sort.value.toString())
            Log.d(
                "mlog: viewModel.sort.equals",
                viewModel.sort.value.equals("created_at").toString()
            )
        }
    }
}