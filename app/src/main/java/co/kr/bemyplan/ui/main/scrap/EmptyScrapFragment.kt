package co.kr.bemyplan.ui.main.scrap

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import co.kr.bemyplan.R
import co.kr.bemyplan.data.main.scrap.ContentModel
import co.kr.bemyplan.databinding.FragmentEmptyScrapBinding
import co.kr.bemyplan.ui.main.scrap.adapter.ScrapRecommendAdapter
import co.kr.bemyplan.ui.purchase.before.BeforePurchaseActivity

class EmptyScrapFragment : Fragment() {
    private var _binding: FragmentEmptyScrapBinding? = null
    private val binding get() = _binding!!
    private lateinit var scrapRecommendAdapter: ScrapRecommendAdapter
    private var listItem = listOf<ContentModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_empty_scrap, container, false)
        initList()
        initRecyclerView()
        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun initList() {
        listItem = listOf(
            ContentModel(R.drawable.rectangle_5715, null, "푸드파이터들을 위한 찐먹킷리스트 투어", true, true),
            ContentModel(R.drawable.img_charge, null, "부모님과 함께하는", true, false),
            ContentModel(R.drawable.rectangle_5715, null, "푸드파이터들을", false, true),
            ContentModel(R.drawable.img_charge, null, "3박 4일 제주 여행", false, false),
            ContentModel(R.drawable.rectangle_5715, null, "푸드파이터들을 위한 찐먹킷리스트 투어", true, false),
        )
    }

    private fun initRecyclerView() {
        scrapRecommendAdapter = ScrapRecommendAdapter(requireContext()) {
            val intent = Intent(requireContext(), BeforePurchaseActivity::class.java)
            startActivity(intent)
        }
        scrapRecommendAdapter.itemList = listItem
        binding.rvRecommend.adapter = scrapRecommendAdapter
    }
}