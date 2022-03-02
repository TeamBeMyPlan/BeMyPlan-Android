package co.kr.bemyplan.ui.main.scrap

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import co.kr.bemyplan.R
import co.kr.bemyplan.data.entity.list.ContentModel
import co.kr.bemyplan.databinding.FragmentEmptyScrapBinding
import co.kr.bemyplan.ui.list.viewmodel.ListViewModel
import co.kr.bemyplan.ui.main.scrap.adapter.ScrapRecommendAdapter
import co.kr.bemyplan.ui.purchase.before.PurchaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EmptyScrapFragment : Fragment() {
    private var _binding: FragmentEmptyScrapBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<ListViewModel>()
    private lateinit var scrapRecommendAdapter: ScrapRecommendAdapter
    private var listItem = listOf<ContentModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding =
            DataBindingUtil.inflate(layoutInflater, R.layout.fragment_empty_scrap, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initList()
        initRecyclerView()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun initList() {
        viewModel.getEmptyScrapList()
        viewModel.emptyScrapList.observe(viewLifecycleOwner) {
            listItem = it
            initRecyclerView()
        }
    }

    private fun initRecyclerView() {
        scrapRecommendAdapter = ScrapRecommendAdapter {
            val intent = Intent(requireContext(), PurchaseActivity::class.java)
            intent.putExtra("postId", it.postId)
            startActivity(intent)
        }
        scrapRecommendAdapter.replaceItem(listItem)
        binding.rvRecommend.adapter = scrapRecommendAdapter
    }
}