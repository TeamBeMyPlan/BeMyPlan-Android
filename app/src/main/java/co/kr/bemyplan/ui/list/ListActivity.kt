package co.kr.bemyplan.ui.list

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import co.kr.bemyplan.R
import co.kr.bemyplan.databinding.ActivityListBinding
import co.kr.bemyplan.ui.list.adapter.ListAdapter
import co.kr.bemyplan.ui.list.viewmodel.ListViewModel
import co.kr.bemyplan.ui.purchase.after.AfterPurchaseActivity
import co.kr.bemyplan.ui.purchase.before.PurchaseActivity
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
    var authorUserId: Int = -1
    var authorNickname: String = ""
    var locationName: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_list)
        from = intent.getStringExtra("from") ?: ""
        region = intent.getStringExtra("region") ?: ""
        authorUserId = intent.getIntExtra("userId", -1)
        locationName = intent.getStringExtra("locationName") ?: ""
        authorNickname = intent.getStringExtra("authorNickname") ?: ""
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
                viewModel.fetchLatestList()
                binding.tvTitle.text = "최신 등록 여행 일정"
                viewModel.latestList.observe(this) { list ->
                    listAdapter.replaceItem(list)
                }
            }
            "suggest" -> {
                Timber.tag("mlog: suggest").d("success")
                binding.layoutSort.visibility = View.GONE
                viewModel.fetchSuggestList()
                binding.tvTitle.text = "비마플 추천 여행 일정"
                viewModel.suggestList.observe(this) { list ->
                    listAdapter.replaceItem(list)
                }
            }
            "location" -> {
                Timber.tag("location").i("success")
                viewModel.fetchLocationList(region, sortViewModel.sort.value.toString())
                binding.tvTitle.text = locationName
                viewModel.locationList.observe(this) { list ->
                    listAdapter.replaceItem(list)
                }
                sortViewModel.sort.observe(this) { sort ->
                    viewModel.fetchLocationList(region, sort)
                }
            }
            "user" -> {
                Timber.tag("mlog: user").d("success")
                viewModel.setAuthorUserId(authorUserId)
                viewModel.fetchUserPlanList(sortViewModel.sort.value.toString())
                binding.tvTitle.text = authorNickname
                viewModel.userPostList.observe(this) { list ->
                    listAdapter.replaceItem(list)
                }
                sortViewModel.sort.observe(this) { sort ->
                    viewModel.fetchUserPlanList(sort)
                }
            }
        }
    }

    private fun initRecyclerView() {
        listAdapter = ListAdapter({
            if (it.orderStatus) {
                val intent = Intent(this, AfterPurchaseActivity::class.java).apply {
                    putExtra("planId", it.planId)
                    putExtra("scrapStatus", it.scrapStatus)
                    putExtra("authorNickname", it.user.nickname)
                    putExtra("authorUserId", it.user.userId)
                }
                startActivity(intent)
            } else {
                val intent = Intent(this, PurchaseActivity::class.java).apply {
                    putExtra("planId", it.planId)
                    putExtra("scrapStatus", it.scrapStatus)
                    putExtra("authorNickname", it.user.nickname)
                    putExtra("authorUserId", it.user.userId)
                }
                startActivity(intent)
            }
        }, {
            viewModel.postScrap(it)
        })
        with(binding) {
            rvLinearContent.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if (!rvLinearContent.canScrollVertically(1)) {
                        when (from) {
                            "new" -> {
                                viewModel.fetchMoreLatestList()
                            }
                            "suggest" -> {
                                viewModel.fetchMoreSuggestList()
                            }
                            "location" -> {
                                viewModel.fetchMoreLocationList(
                                    region,
                                    sortViewModel.sort.value.toString()
                                )
                            }
                            "user" -> {
                                viewModel.fetchMoreUserPlanList(sortViewModel.sort.value.toString())
                            }
                        }
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