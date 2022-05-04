package co.kr.bemyplan.ui.list

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import co.kr.bemyplan.R
import co.kr.bemyplan.databinding.ActivityListBinding
import co.kr.bemyplan.ui.list.adapter.ListAdapter
import co.kr.bemyplan.ui.list.viewmodel.ListViewModel
import co.kr.bemyplan.ui.purchase.before.PurchaseActivity
import co.kr.bemyplan.ui.purchase.after.AfterPurchaseActivity
import co.kr.bemyplan.ui.sort.SortFragment
import co.kr.bemyplan.ui.sort.viewmodel.SortViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class ListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityListBinding
    private val viewModel by viewModels<ListViewModel>()
    private val sortViewModel by viewModels<SortViewModel>()
    private lateinit var listAdapter: ListAdapter
    var from: String = ""
    var region: String = ""
    var userId: Int = -1
    var authorNickname: String = ""
    var locationName: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_list)
        from = intent.getStringExtra("from") ?: ""
        region = intent.getStringExtra("region") ?: ""
        userId = intent.getIntExtra("userId", -1)
        locationName = intent.getStringExtra("locationName") ?: ""
        authorNickname = intent.getStringExtra("authorNickname") ?: ""
        Timber.tag("mlog: ListActivity.userId").d(userId.toString())
        Timber.tag("mlog: ListActivity.userId").d(authorNickname)
        initList(from)
        initRecyclerView()
        clickBack()
        openBottomSheetDialog()
    }

    private fun initList(from: String?) {
        when (from) {
            "new" -> {
                Timber.tag("mlog: new").d("success")
                binding.layoutSort.visibility = View.GONE
                viewModel.getLatestList()
                binding.tvTitle.text = "최신 등록 여행 일정"
                viewModel.latestList.observe(this) {
                    listAdapter.replaceItem(it)
                }
            }
            "suggest" -> {
                Timber.tag("mlog: suggest").d("success")
                binding.layoutSort.visibility = View.GONE
                viewModel.getSuggestList()
                binding.tvTitle.text = "비마플 추천 여행 일정"
                viewModel.suggestList.observe(this) {
                    listAdapter.replaceItem(it)
                }
            }
            "location" -> {
                Timber.tag("location").i("success")
                viewModel.getLocationList(region, sortViewModel.sort.value.toString())
                binding.tvTitle.text = locationName
                viewModel.locationList.observe(this) { list ->
                    listAdapter.replaceItem(list)
                }
                sortViewModel.sort.observe(this) { sort ->
                    viewModel.getLocationList(region, sort)
                }
            }
            "user" -> {
                Timber.tag("mlog: user").d("success")
                viewModel.getUserPostList(userId, sortViewModel.sort.value.toString())
                binding.tvTitle.text = authorNickname
                viewModel.userPostList.observe(this) {
                    listAdapter.replaceItem(it)
                }
                sortViewModel.sort.observe(this) {
                    viewModel.getUserPostList(userId, it)
                }
            }
        }
    }

    private fun initRecyclerView() {
        listAdapter = ListAdapter({
            if (it.orderStatus) {
                val intent = Intent(this, AfterPurchaseActivity::class.java).apply {
                    putExtra("postId", it.planId)
                    putExtra("scrapStatus", it.scrapStatus)
                }
                Timber.tag("mlog: putExtra에서 postId").d(it.planId.toString())
                startActivity(intent)
            } else {
                val intent = Intent(this, PurchaseActivity::class.java).apply {
                    putExtra("postId", it.planId)
                    putExtra("scrapStatus", it.scrapStatus)
                }
                Timber.tag("mlog: putExtra에서 postId").d(it.planId.toString())
                startActivity(intent)
            }
        }, {
            viewModel.postScrap(it)
        })
        with(binding) {
            rvLinearContent.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if(!rvLinearContent.canScrollVertically(1)) {
                        viewModel.getMoreLocationList(region, sortViewModel.sort.value.toString())
                    }
                }
            })
            rvLinearContent.adapter = listAdapter
        }
    }

    private fun clickBack() {
        binding.layoutBack.setOnClickListener {
            finish()
        }
    }

    private fun openBottomSheetDialog() {
        binding.ivOrder.setOnClickListener {
            val bottomSheetDialogFragment = SortFragment()
            bottomSheetDialogFragment.show(supportFragmentManager, bottomSheetDialogFragment.tag)
            Timber.tag("mlog: ListActivity에서의 sort").d(sortViewModel.sort.value.toString())
        }
    }
}