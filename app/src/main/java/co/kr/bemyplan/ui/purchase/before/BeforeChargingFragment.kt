package co.kr.bemyplan.ui.purchase.before

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.NestedScrollView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import co.kr.bemyplan.R
import co.kr.bemyplan.databinding.FragmentBeforeChargingBinding
import co.kr.bemyplan.ui.list.ListActivity
import co.kr.bemyplan.ui.purchase.after.AfterPurchaseActivity
import co.kr.bemyplan.ui.purchase.before.adapter.ContentAdapter
import co.kr.bemyplan.ui.purchase.before.viewmodel.BeforeChargingViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BeforeChargingFragment : Fragment() {
    private var _binding: FragmentBeforeChargingBinding? = null
    private val binding get() = _binding ?: error("Binding이 초기화 되지 않았습니다.")
    private val viewModel by activityViewModels<BeforeChargingViewModel>()
    private lateinit var contentAdapter: ContentAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_before_charging, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initList()
        initRecyclerView()
        initNestedScrollView()
        clickBack()
        clickNickname()
        clickPurchase()
        showExample()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun initList() {
        viewModel.fetchPreviewPlan()
        viewModel.previewInfo.observe(viewLifecycleOwner) { previewInfo ->
            binding.info = previewInfo
        }
//        viewModel.previewContents.observe(viewLifecycleOwner) { previewContents ->
//            contentAdapter.replaceItem(previewContents)
//        }
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
            val intent = Intent(requireContext(), ListActivity::class.java)
            intent.putExtra("from", "user")
//            intent.putExtra("userId", viewModel.previewInformation.value?.authorId)
//            intent.putExtra("authorNickname", viewModel.previewInformation.value?.author)
//            Log.d(
//                "mlog: beforecharging.author_id",
//                viewModel.previewInformation.value?.authorId.toString()
//            )
            startActivity(intent)
        }
    }

    private fun showExample() {
        binding.tvPurchaseExample.setOnClickListener {
            val intent = Intent(requireContext(), AfterPurchaseActivity::class.java)
            intent.putExtra("postId", -1)
            startActivity(intent)
        }
    }

    private fun clickPurchase() {
        binding.layoutPurchase.setOnClickListener {
            val chargingFragment = ChargingFragment()
            val transaction = parentFragmentManager.beginTransaction()
            transaction.add(R.id.fragment_container_charging, chargingFragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }
    }
}