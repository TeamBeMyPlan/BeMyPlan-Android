package co.kr.bemyplan.ui.main.scrap

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import co.kr.bemyplan.R
import co.kr.bemyplan.data.entity.list.ContentModel
import co.kr.bemyplan.databinding.FragmentNotEmptyScrapBinding
import co.kr.bemyplan.ui.list.viewmodel.ListViewModel
import co.kr.bemyplan.ui.main.scrap.adapter.ScrapAdapter
import co.kr.bemyplan.ui.purchase.after.AfterPurchaseActivity
import co.kr.bemyplan.ui.purchase.before.PurchaseActivity
import co.kr.bemyplan.ui.sort.SortFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotEmptyScrapFragment : Fragment() {
    private var _binding: FragmentNotEmptyScrapBinding? = null
    private val binding get() = _binding!!
    private val viewModel by activityViewModels<ListViewModel>()
    private lateinit var scrapAdapter: ScrapAdapter
    private var listItem = listOf<ContentModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_not_empty_scrap, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initList()
        reloadList()
        openBottomSheetDialog()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun initList() {
        viewModel.scrapList.observe(viewLifecycleOwner) {
            listItem = it
            initRecyclerView()
            Log.d("mlog: NotEmptyScrapFragment.initList", "execute")
        }
    }

    private fun reloadList() {
        viewModel.sort.observe(viewLifecycleOwner) {
            viewModel.getScrapList()
            Log.d("mlog: viewModel.sort.value", viewModel.sort.value.toString())
        }
    }

    private fun initRecyclerView() {
        scrapAdapter = ScrapAdapter({
            if (it.isPurchased) {
                val intent = Intent(requireContext(), AfterPurchaseActivity::class.java)
                intent.putExtra("postId", it.postId)
                startActivity(intent)
            } else {
                val intent = Intent(requireContext(), PurchaseActivity::class.java)
                intent.putExtra("postId", it.postId)
                // TODO : 언젠가는 고쳐야 함
                intent.putExtra("isScraped", true)
                startActivity(intent)
            }
        }, {
            viewModel.postScrap(it)
        })
        scrapAdapter.replaceItem(listItem)
        binding.rvContent.adapter = scrapAdapter
    }

    private fun openBottomSheetDialog() {
        binding.layoutOrder.setOnClickListener {
            val bottomSheetDialogFragment = SortFragment()
            bottomSheetDialogFragment.show(childFragmentManager, bottomSheetDialogFragment.tag)
        }
    }
}