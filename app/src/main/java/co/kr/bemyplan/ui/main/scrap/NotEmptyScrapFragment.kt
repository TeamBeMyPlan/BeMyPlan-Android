package co.kr.bemyplan.ui.main.scrap

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import co.kr.bemyplan.R
import co.kr.bemyplan.databinding.FragmentNotEmptyScrapBinding
import co.kr.bemyplan.ui.main.scrap.adapter.ScrapAdapter
import co.kr.bemyplan.ui.main.scrap.viewmodel.ScrapViewModel
import co.kr.bemyplan.ui.purchase.after.AfterPurchaseActivity
import co.kr.bemyplan.ui.purchase.before.PurchaseActivity
import co.kr.bemyplan.ui.sort.SortFragment
import co.kr.bemyplan.ui.sort.viewmodel.SortViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class NotEmptyScrapFragment : Fragment() {
    private var _binding: FragmentNotEmptyScrapBinding? = null
    private val binding get() = _binding!!
    private val viewModel by activityViewModels<ScrapViewModel>()
    private val sortViewModel by activityViewModels<SortViewModel>()
    private lateinit var scrapAdapter: ScrapAdapter
    private val planActivityResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == AppCompatActivity.RESULT_OK) {
                it.data?.let { intent ->
                    val scrapStatusFromPlanActivity = intent.getBooleanExtra("scrapStatus", false)
                    val planIdFromPlanActivity = intent.getIntExtra("planId", -1)
                    Timber.tag("scrapStatusFromPlanActivity").i(scrapStatusFromPlanActivity.toString())
                    Timber.tag("planIdFromPlanActivity").i(planIdFromPlanActivity.toString())
                    scrapAdapter.updateItem(scrapStatusFromPlanActivity, planIdFromPlanActivity)
                }
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_not_empty_scrap, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        observeData()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun initView() {
        initRecyclerView()
        openBottomSheetDialog()
    }

    private fun observeData() {
        viewModel.scrapList.observe(viewLifecycleOwner) {
            scrapAdapter.replaceItem(it)
        }
        sortViewModel.sort.observe(viewLifecycleOwner) {
            viewModel.getScrapList(it)
        }
    }

    private fun initRecyclerView() {
        scrapAdapter = ScrapAdapter({
            viewModel.checkPurchased(it.planId)
            observeDataForStartActivity(it.planId, it.user.nickname, it.user.userId, it.thumbnailUrl)
        }, { planId, scrapStatus ->
            when (scrapStatus) {
                true -> viewModel.deleteScrap(planId)
                false -> viewModel.postScrap(planId)
            }
        })
        binding.rvContent.adapter = scrapAdapter
    }

    private fun openBottomSheetDialog() {
        binding.layoutOrder.setOnClickListener {
            val bottomSheetDialogFragment = SortFragment()
            bottomSheetDialogFragment.show(childFragmentManager, bottomSheetDialogFragment.tag)
        }
    }

    private fun observeDataForStartActivity(planId: Int, authorNickname: String, authorUserId: Int, thumbnail: String) {
        viewModel.isPurchased.observe(viewLifecycleOwner) {
            val intent = Intent(requireContext(), AfterPurchaseActivity::class.java).apply {
                putExtra("planId", planId)
                putExtra("authorNickname", authorNickname)
                putExtra("authorUserId", authorUserId)
            }
            planActivityResultLauncher.launch(intent)
        }
        viewModel.isNotPurchased.observe(viewLifecycleOwner) {
            val intent = Intent(requireContext(), PurchaseActivity::class.java).apply {
                putExtra("planId", planId)
                putExtra("authorNickname", authorNickname)
                putExtra("authorUserId", authorUserId)
                putExtra("thumbnail", thumbnail)
            }
            planActivityResultLauncher.launch(intent)
        }
    }
}