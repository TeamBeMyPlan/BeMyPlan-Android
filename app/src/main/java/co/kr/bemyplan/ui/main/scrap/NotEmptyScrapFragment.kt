package co.kr.bemyplan.ui.main.scrap

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import co.kr.bemyplan.R
import co.kr.bemyplan.data.main.scrap.ContentModel
import co.kr.bemyplan.databinding.FragmentNotEmptyScrapBinding
import co.kr.bemyplan.ui.main.scrap.adapter.ScrapAdapter
import co.kr.bemyplan.ui.purchase.before.BeforePurchaseActivity
import co.kr.bemyplan.ui.sort.SortFragment

class NotEmptyScrapFragment : Fragment() {
    private var _binding: FragmentNotEmptyScrapBinding? = null
    private val binding get() = _binding!!
    private lateinit var scrapAdapter: ScrapAdapter
    private var listItem = listOf<ContentModel>()
    private var orderBy: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_not_empty_scrap, container, false)
        initList()
        initRecyclerView()
        openBottomSheetDialog()
        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun initList() {
        listItem = listOf(
            ContentModel(R.drawable.rectangle_5715, null, "푸드파이터들을 위한 찐먹킷리스트 투어", true, true),
            ContentModel(R.drawable.img, null, "부모님과 함께하는", true, false),
            ContentModel(R.drawable.rectangle_5715, null, "푸드파이터들을", false, true),
            ContentModel(R.drawable.img, null, "3박 4일 제주 여행", false, false),
            ContentModel(R.drawable.rectangle_5715, null, "푸드파이터들을 위한 찐먹킷리스트 투어", true, false),
            ContentModel(R.drawable.img, null, "부모님과 함께하는 3박 4일 제주 여행", false, true),
        )
    }

    private fun initRecyclerView() {
        scrapAdapter = ScrapAdapter {
            val intent = Intent(requireContext(), BeforePurchaseActivity::class.java)
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