package co.kr.bemyplan.ui.purchase.before

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.NestedScrollView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.add
import androidx.fragment.app.commit
import co.kr.bemyplan.R
import co.kr.bemyplan.data.local.BeMyPlanDataStore
import co.kr.bemyplan.databinding.FragmentBeforeChargingBinding
import co.kr.bemyplan.ui.list.ListActivity
import co.kr.bemyplan.ui.login.LoginActivity
import co.kr.bemyplan.ui.purchase.after.AfterPurchaseActivity
import co.kr.bemyplan.ui.purchase.before.adapter.ContentAdapter
import co.kr.bemyplan.ui.purchase.before.viewmodel.BeforeChargingViewModel
import co.kr.bemyplan.util.CustomDialog
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class BeforeChargingFragment : Fragment() {
    private var _binding: FragmentBeforeChargingBinding? = null
    private val binding get() = _binding ?: error("Binding이 초기화 되지 않았습니다.")
    private val viewModel by activityViewModels<BeforeChargingViewModel>()
    private lateinit var contentAdapter: ContentAdapter

    @Inject
    lateinit var beMyPlanDataStore: BeMyPlanDataStore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_before_charging, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initNetwork()
        observeData()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun initView() {
        initRecyclerView()
        initNestedScrollView()
        clickBack()
        clickNickname()
        clickPurchase()
        clickScrap()
        showExample()
    }

    private fun initNetwork() {
        viewModel.fetchPreviewPlan()
        viewModel.checkScrapStatus()
    }

    private fun observeData() {
        viewModel.previewInfo.observe(viewLifecycleOwner) { previewInfo ->
            binding.info = previewInfo
        }
        viewModel.previewContent.observe(viewLifecycleOwner) { previewContent ->
            contentAdapter.replaceItem(previewContent)
        }
    }

    private fun initRecyclerView() {
        contentAdapter = ContentAdapter()
        binding.rvContent.adapter = contentAdapter
    }

    private fun initNestedScrollView() {
        var isVisible = true
        binding.nsv.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            if (scrollY < oldScrollY) {
                // 하단 View 표시하기
                if (!isVisible) {
                    isVisible = true
                    showLayoutPurchase()
                }
                // 타이틀 상단에 걸기
                setTopTitle()
            } else if (scrollY > oldScrollY) {
                if (scrollY == v.getChildAt(0).measuredHeight - v.measuredHeight) {
                    // 하단 View 보이기
                    isVisible = true
                    showLayoutPurchase()
                } else {
                    // 하단 View 숨기기
                    if (isVisible) {
                        isVisible = false
                        hideLayoutPurchase()
                    }
                }
                // 타이틀 상단에 걸기
                setTopTitle()
            }
        })
    }

    private fun showLayoutPurchase() {
        val layout = binding.layoutPurchase
        layout.animate()
            .translationY(0f)
            .alpha(1f)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationStart(animation: Animator?) {
                    super.onAnimationStart(animation)
                    layout.visibility = View.VISIBLE
                }
            })
    }

    private fun hideLayoutPurchase() {
        val layout = binding.layoutPurchase
        layout.animate()
            .translationY(layout.height.toFloat())
            .alpha(0f)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    super.onAnimationEnd(animation)
                    binding.layoutPurchase.visibility = View.GONE
                }
            })
    }

    private fun setTopTitle() {
        val rect = Rect()
        binding.nsv.getHitRect(rect)
        if (binding.tvTitle.getLocalVisibleRect(rect)) {
            // view가 보이는 경우
            binding.tvTopTitle.visibility = View.INVISIBLE
        } else {
            // view가 안 보이는 경우
            binding.tvTopTitle.visibility = View.VISIBLE
        }
    }

    private fun clickBack() {
        binding.layoutBack.setOnClickListener {
            requireActivity().finish()
        }
    }

    private fun clickNickname() {
        binding.layoutAuthor.setOnClickListener {
//            viewModel.firebaseAnalyticsProvider.firebaseAnalytics.logEvent(
//                "clickEditorName",
//                Bundle().apply {
//                    putString("source", "구매 전 여행일정 미리보기")
//                })
//            val intent = Intent(requireContext(), ListActivity::class.java).apply {
//                putExtra("from", "user")
//                putExtra("authorUserId", viewModel.authorUserId)
//                putExtra("authorNickname", viewModel.authorNickname)
//            }
//            startActivity(intent)
        }
    }

    private fun showExample() {
        binding.tvPurchaseExample.setOnClickListener {
            viewModel.firebaseAnalyticsProvider.firebaseAnalytics.logEvent(
                "clickPlanDetailExample",
                null
            )
            val intent = Intent(requireContext(), AfterPurchaseActivity::class.java)
            intent.putExtra("postId", -1)
            startActivity(intent)
        }
    }

    private fun clickPurchase() {
        binding.layoutPurchase.setOnClickListener {
            if (beMyPlanDataStore.userId != 0) {
                parentFragmentManager.commit {
                    add<ChargingFragment>(R.id.fragment_container_charging)
                    addToBackStack(null)
                }
            } else {
                val dialog = CustomDialog(requireContext(), "", "")
                dialog.setOnClickedListener(object : CustomDialog.ButtonClickListener {
                    override fun onClicked(num: Int) {
                        if (num == 1) {
                            val intent = Intent(requireContext(), LoginActivity::class.java)
                            startActivity(intent)
                            requireActivity().finishAffinity()
                        }
                    }
                })
            }
        }
    }

    private fun clickScrap() {
        binding.layoutScrap.setOnClickListener {
            if (beMyPlanDataStore.userId != 0) {
                viewModel.scrap()
            } else {
                val dialog = CustomDialog(requireContext(), "", "")
                dialog.setOnClickedListener(object : CustomDialog.ButtonClickListener {
                    override fun onClicked(num: Int) {
                        if (num == 1) {
                            val intent = Intent(requireContext(), LoginActivity::class.java)
                            startActivity(intent)
                            requireActivity().finishAffinity()
                        }
                    }
                })
            }
        }
    }
}