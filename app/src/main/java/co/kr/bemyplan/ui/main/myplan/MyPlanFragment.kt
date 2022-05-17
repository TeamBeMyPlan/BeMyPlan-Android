package co.kr.bemyplan.ui.main.myplan

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.kr.bemyplan.R
import co.kr.bemyplan.databinding.FragmentMyPlanBinding
import co.kr.bemyplan.domain.model.list.ContentModel
import co.kr.bemyplan.ui.list.adapter.ListAdapter
import co.kr.bemyplan.ui.main.myplan.adapter.MyPlanAdapter
import co.kr.bemyplan.ui.main.myplan.settings.SettingsActivity
import co.kr.bemyplan.ui.main.myplan.viewmodel.MyPlanViewModel
import co.kr.bemyplan.ui.purchase.after.AfterPurchaseActivity
import co.kr.bemyplan.ui.purchase.before.PurchaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyPlanFragment : Fragment() {
    private var _binding: FragmentMyPlanBinding? = null
    private val binding get() = _binding ?: error("Binding이 초기화 되지 않았습니다.")
    private val viewModel by viewModels<MyPlanViewModel>()
    private var listItem = listOf<ContentModel>()
    private lateinit var purchaseTourAdapter: MyPlanAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_plan, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        initList()
        initSettingsButton()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        viewModel.setNickname(AutoLoginData.getNickname(requireContext()))
    }

    private fun initList() {
        viewModel.fetchMyPlanList()
        viewModel.myPlanList.observe(viewLifecycleOwner) {
            listItem = it
            if (listItem.isEmpty()) {
                lookingAroundEvent()
            } else {
                initRecyclerView()
            }
        }
    }

    private fun initAdapter() {
        binding.rvMyPlanPurchase.layoutManager = GridLayoutManager(requireContext(), 2)
        purchaseTourAdapter = MyPlanAdapter({
            val intent = Intent(requireContext(), AfterPurchaseActivity::class.java)
            intent.putExtra("postId", it.planId)
            startActivity(intent)
        }, {
            viewModel.postScrap(it)
        })
        viewModel.myPlanList.observe(viewLifecycleOwner) { list ->
            purchaseTourAdapter.replaceItem(list)
        }
        binding.rvMyPlanPurchase.adapter = purchaseTourAdapter
    }

    private fun initRecyclerView() {
        purchaseTourAdapter = MyPlanAdapter({
            val intent = Intent(requireContext(), AfterPurchaseActivity::class.java)
            intent.putExtra("postId", it.planId)
            startActivity(intent)
        }, {
            viewModel.postScrap(it)
        })
        with(binding) {
            rvMyPlanPurchase.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if (!rvMyPlanPurchase.canScrollVertically(1)) {
                        viewModel?.fetchMoreMyPlanList()
                    }
                }
            })
            rvMyPlanPurchase.adapter = purchaseTourAdapter
        }
    }

    private fun lookingAroundEvent() {
        binding.tvLookingAround.setOnClickListener {
//            Navigation Component로 변경함에 따른 주석처리
//            (activity as MainActivity).moveHome()
            Navigation.findNavController(binding.root).navigate(R.id.action_fragment_my_plan_to_fragment_home)
        }
    }

    private fun initSettingsButton() {
        binding.clSettings.setOnClickListener {
            val intent = Intent(activity, SettingsActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}