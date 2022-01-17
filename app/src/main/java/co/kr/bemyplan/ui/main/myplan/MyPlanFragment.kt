package co.kr.bemyplan.ui.main.myplan

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import co.kr.bemyplan.R
import co.kr.bemyplan.data.entity.main.myplan.Profile
import co.kr.bemyplan.data.entity.main.myplan.PurchaseTour
import co.kr.bemyplan.databinding.FragmentMyPlanBinding
import co.kr.bemyplan.ui.main.MainActivity
import co.kr.bemyplan.ui.main.myplan.adapter.MyPlanAdapter
import co.kr.bemyplan.ui.main.myplan.settings.SettingsActivity

class MyPlanFragment : Fragment() {
    private lateinit var purchaseTourAdapter: MyPlanAdapter
    private var _binding: FragmentMyPlanBinding? = null
    private val binding get() = _binding ?: error("Binding이 초기화 되지 않았습니다.")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_plan, container, false)
        initProfile()
        initSettingsButton()
        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun initProfile() {
        val purchaseCount = 0
        binding.profile = Profile("다운타운베이비", R.drawable.ic_img_profile, purchaseCount)

        if(purchaseCount != 0) initAdapter()
        else lookingAroundEvent()
    }

    private fun initAdapter() {
        binding.rvMyPlanPurchase.layoutManager = GridLayoutManager(context, 2)
        purchaseTourAdapter = MyPlanAdapter()

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

    private fun lookingAroundEvent() {
        binding.tvLookingAround.setOnClickListener {
            (activity as MainActivity).moveHome()
        }
    }

    private fun initSettingsButton() {
        binding.ivSettings.setOnClickListener {
            val intent = Intent(activity, SettingsActivity::class.java)
            startActivity(intent)
        }
    }
}