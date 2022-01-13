package co.kr.bemyplan.ui.main.myplan.exist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import co.kr.bemyplan.R
import co.kr.bemyplan.data.myplan.PurchaseTour
import co.kr.bemyplan.databinding.FragmentExistMyPlanBinding
import co.kr.bemyplan.ui.main.myplan.exist.adapter.ExistMyPlanAdapter

class ExistMyPlanFragment : Fragment() {
    private lateinit var purchaseTourAdapter: ExistMyPlanAdapter
    private var _binding: FragmentExistMyPlanBinding? = null
    private val binding get() = _binding ?: error("Binding이 초기화 되지 않았습니다.")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentExistMyPlanBinding.inflate(layoutInflater, container, false)
        initAdapter()

        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun initAdapter() {
        binding.rvMyPlanPurchase.layoutManager = GridLayoutManager(context, 2)
        purchaseTourAdapter = ExistMyPlanAdapter()

        val items = listOf(
            PurchaseTour("27년 제주 토박이의 히든 플레이스 투어", R.drawable.img_home_editor),
            PurchaseTour("워케이션을 위한 카페투어", R.drawable.img_home_popular),
            PurchaseTour("27년 제주 토박이의 히든 플레이스 투어", R.drawable.img_home_editor),
            PurchaseTour("워케이션을 위한 카페투어", R.drawable.img_home_popular),
            PurchaseTour("27년 제주 토박이의 히든 플레이스 투어", R.drawable.img_home_editor),
            PurchaseTour("워케이션을 위한 카페투어", R.drawable.img_home_popular)
        )

        purchaseTourAdapter.setItems(items)
        binding.rvMyPlanPurchase.adapter = purchaseTourAdapter
    }
}