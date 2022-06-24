package co.kr.bemyplan.ui.main.myplan

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import co.kr.bemyplan.R
import co.kr.bemyplan.databinding.FragmentMyPlanBinding
import co.kr.bemyplan.domain.model.main.myplan.MyPlanData
import co.kr.bemyplan.ui.login.LoginActivity
import co.kr.bemyplan.ui.main.myplan.adapter.MyPlanAdapter
import co.kr.bemyplan.ui.main.myplan.settings.SettingsActivity
import co.kr.bemyplan.ui.main.myplan.viewmodel.MyPlanViewModel
import co.kr.bemyplan.ui.purchase.after.AfterPurchaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyPlanFragment : Fragment() {
    private var _binding: FragmentMyPlanBinding? = null
    private val binding get() = _binding ?: error("Binding이 초기화 되지 않았습니다.")
    private val viewModel by viewModels<MyPlanViewModel>()
    private var listItem = listOf<MyPlanData.Data>()
    private lateinit var purchaseTourAdapter: MyPlanAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_plan, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initList()
        initAdapter()
        lookingAroundEvent()
        initSettingsButton()
        clickLogin()
        observeList()
    }

    private fun observeList() {
        viewModel.myPlan.observe(viewLifecycleOwner) {
            purchaseTourAdapter.submitList(it.toMutableList())
        }
    }

    private fun initList() {
        viewModel.getMyPlanList()
    }

    private fun initAdapter() {
        purchaseTourAdapter = MyPlanAdapter({
            viewModel.firebaseAnalyticsProvider.firebaseAnalytics.logEvent(
                "clickTravelPlan",
                Bundle().apply {
                    putString("source", "마이플랜")
                    putInt("planId", it.planId)
                })
            val intent = Intent(requireContext(), AfterPurchaseActivity::class.java).apply {
                putExtra("planId", it.planId)
                putExtra("authorNickName", it.user.nickname)
                putExtra("authorUserId", it.user.userId)
            }
            startActivity(intent)
        }, {
            when (it.scrapStatus) {
                true -> viewModel.deleteScrap(it.planId)
                false -> viewModel.postScrap(it.planId)
            }
        })
        binding.rvMyPlanPurchase.adapter = purchaseTourAdapter
        ViewCompat.setNestedScrollingEnabled(binding.rvMyPlanPurchase, false)

        val scroll = binding.sv
        scroll.viewTreeObserver.addOnScrollChangedListener {
            val view = scroll.getChildAt(scroll.childCount - 1)
            val diff = view.bottom - (scroll.height + scroll.scrollY)
            if (diff == 0) {
                Log.d("asdf", "getMoreMyPlanList()")
                viewModel.getMoreMyPlanList()
            }
        }
    }

    private fun lookingAroundEvent() {
        if (listItem.isEmpty()) {
            binding.tvLookingAround.setOnClickListener {
                Navigation.findNavController(binding.root)
                    .navigate(R.id.action_fragment_my_plan_to_fragment_home)
            }
        }
    }

    private fun initSettingsButton() {
        binding.clSettings.setOnClickListener {
            val intent = Intent(activity, SettingsActivity::class.java)
            startActivity(intent)
        }
    }

    private fun clickLogin() {
        binding.layoutLogin.setOnClickListener {
            val intent = Intent(requireContext(), LoginActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            }
            startActivity(intent)
            activity?.finish()
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}