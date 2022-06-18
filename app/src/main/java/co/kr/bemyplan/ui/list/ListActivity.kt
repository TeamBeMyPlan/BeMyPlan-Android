package co.kr.bemyplan.ui.list

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
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
    private val planActivityResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK) {
                it.data?.let { intent ->
                    val scrapStatusFromPlanActivity = intent.getBooleanExtra("scrapStatus", false)
                    val planIdFromPlanActivity = intent.getIntExtra("planId", -1)
                    listAdapter.updateItem(scrapStatusFromPlanActivity, planIdFromPlanActivity)
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_list)
        initView()
        observeData()
    }

    override fun onResume() {
        super.onResume()
        viewModel.isPurchased.removeObservers(this)
        viewModel.isNotPurchased.removeObservers(this)
    }

    private fun initView() {
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
            }
            "suggest" -> {
                Timber.tag("mlog: suggest").d("success")
                binding.layoutSort.visibility = View.GONE
                viewModel.fetchSuggestList()
                binding.tvTitle.text = "비마플 추천 여행 일정"
            }
            "location" -> {
                Timber.tag("location").i("success")
                viewModel.fetchLocationList(region, sortViewModel.sort.value.toString())
                binding.tvTitle.text = locationName
            }
            "user" -> {
                Timber.tag("mlog: user").d("success")
                viewModel.setAuthorUserId(authorUserId)
                viewModel.fetchUserPlanList(sortViewModel.sort.value.toString())
                binding.tvTitle.text = authorNickname
            }
        }
    }

    private fun initRecyclerView() {
        listAdapter = ListAdapter({
            viewModel.checkPurchased(it.planId)
            observeDataForStartActivity(
                it.planId,
                it.user.nickname,
                it.user.userId,
                it.thumbnailUrl
            )
        }, { planId, scrapStatus ->
            when (scrapStatus) {
                true -> viewModel.deleteScrap(planId)
                false -> viewModel.postScrap(planId)
            }
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

    private fun observeData() {
        when (from) {
            "new" -> {
                viewModel.latestList.observe(this) { list ->
                    listAdapter.replaceItem(list)
                }
            }
            "suggest" -> {
                viewModel.suggestList.observe(this) { list ->
                    listAdapter.replaceItem(list)
                }
            }
            "location" -> {
                viewModel.locationList.observe(this) { list ->
                    listAdapter.replaceItem(list)
                }
                sortViewModel.sort.observe(this) { sort ->
                    viewModel.fetchLocationList(region, sort)
                }
            }
            "user" -> {
                viewModel.userPostList.observe(this) { list ->
                    listAdapter.replaceItem(list)
                }
                sortViewModel.sort.observe(this) { sort ->
                    viewModel.fetchLocationList(region, sort)
                }
            }
        }
    }

    private fun observeDataForStartActivity(
        planId: Int,
        authorNickname: String,
        authorUserId: Int,
        thumbnail: String
    ) {
        viewModel.isPurchased.observe(this) {
            val intent = Intent(this, AfterPurchaseActivity::class.java).apply {
                putExtra("planId", planId)
                putExtra("authorNickname", authorNickname)
                putExtra("authorUserId", authorUserId)
            }
            planActivityResultLauncher.launch(intent)
        }
        viewModel.isNotPurchased.observe(this) {
            val intent = Intent(this, PurchaseActivity::class.java).apply {
                putExtra("planId", planId)
                putExtra("authorNickname", authorNickname)
                putExtra("authorUserId", authorUserId)
                putExtra("thumbnail", thumbnail)
            }
            planActivityResultLauncher.launch(intent)
        }
    }
}