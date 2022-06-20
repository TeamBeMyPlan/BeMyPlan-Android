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
import co.kr.bemyplan.R
import co.kr.bemyplan.data.firebase.FirebaseAnalyticsProvider
import co.kr.bemyplan.databinding.FragmentMyPlanBinding
import co.kr.bemyplan.domain.model.main.myplan.MyPlanData
import co.kr.bemyplan.ui.login.LoginActivity
import co.kr.bemyplan.ui.main.myplan.adapter.MyPlanAdapter
import co.kr.bemyplan.ui.main.myplan.settings.SettingsActivity
import co.kr.bemyplan.ui.main.myplan.viewmodel.MyPlanViewModel
import co.kr.bemyplan.ui.purchase.after.AfterPurchaseActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MyPlanFragment : Fragment() {
    private var _binding: FragmentMyPlanBinding? = null
    private val binding get() = _binding ?: error("Binding이 초기화 되지 않았습니다.")
    private val viewModel by viewModels<MyPlanViewModel>()
    private var listItem = listOf<MyPlanData.Data>()
    private lateinit var purchaseTourAdapter: MyPlanAdapter

    @Inject
    lateinit var firebaseAnalyticsProvider: FirebaseAnalyticsProvider

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
        lookingAroundEvent()
        initSettingsButton()
        clickLogin()
    }

    private fun initList() {
        viewModel.getMyPlanList()
        viewModel.myPlan.observe(viewLifecycleOwner) {
            listItem = it
            initAdapter()
        }
    }

    private fun initAdapter() {
        purchaseTourAdapter = MyPlanAdapter({
            firebaseAnalyticsProvider.firebaseAnalytics.logEvent("clickTravelPlan", Bundle().apply {
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
        /*binding.rvMyPlanPurchase.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if(!binding.rvMyPlanPurchase.canScrollVertically(1)){
                    viewModel.getMoreMyPlanList()
                }
            }
        })*/
        purchaseTourAdapter.submitList(listItem)
        binding.rvMyPlanPurchase.adapter = purchaseTourAdapter
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