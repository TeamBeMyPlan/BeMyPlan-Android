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
import co.kr.bemyplan.data.entity.main.scrap.ContentModel
import co.kr.bemyplan.databinding.FragmentNotEmptyScrapBinding
import co.kr.bemyplan.ui.main.scrap.adapter.ScrapAdapter
import co.kr.bemyplan.ui.main.scrap.viewmodel.ScrapViewModel
import co.kr.bemyplan.ui.purchase.PurchaseActivity
import co.kr.bemyplan.ui.sort.SortFragment

class NotEmptyScrapFragment : Fragment() {
    private var _binding: FragmentNotEmptyScrapBinding? = null
    private val binding get() = _binding!!
    private val viewModel by activityViewModels<ScrapViewModel>()
    private lateinit var scrapAdapter: ScrapAdapter
    private var listItem = listOf<ContentModel>()
    private var orderBy: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_not_empty_scrap, container, false)
        initList()
//        initRecyclerView()
        openBottomSheetDialog()
        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun initList() {
//        viewModel.scrapList.observe(viewLifecycleOwner) {
//            listItem = it
//            initRecyclerView()
//        }
        listItem = viewModel.scrapList.value!!
        initRecyclerView()
    }

    private fun initRecyclerView() {
        scrapAdapter = ScrapAdapter {
            val intent = Intent(requireContext(), PurchaseActivity::class.java)
            startActivity(intent)
        }
        scrapAdapter.itemList = listItem
        binding.rvContent.adapter = scrapAdapter
    }

    private fun openBottomSheetDialog() {
        binding.ivOrder.setOnClickListener {
            val bottomSheetDialogFragment = SortFragment {
                orderBy = it
                Log.d("mlog: orderBy", orderBy.toString())
            }
            bottomSheetDialogFragment.show(childFragmentManager, bottomSheetDialogFragment.tag)
        }
    }
}