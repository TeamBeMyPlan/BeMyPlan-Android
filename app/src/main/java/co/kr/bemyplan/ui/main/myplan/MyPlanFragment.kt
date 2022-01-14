package co.kr.bemyplan.ui.main.myplan

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import co.kr.bemyplan.R
import co.kr.bemyplan.data.myplan.Profile
import co.kr.bemyplan.databinding.FragmentMyPlanBinding
import co.kr.bemyplan.ui.main.myplan.exist.ExistMyPlanFragment
import co.kr.bemyplan.ui.purchase.after.DailyContentsFragment
import com.bumptech.glide.Glide

class MyPlanFragment : Fragment() {
    private var _binding: FragmentMyPlanBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_plan, container, false)
        initProfile()
        initFragment()
        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun initProfile() {
        binding.profile = Profile("다운타운베이비", R.drawable.ic_img_profile, 0)
    }

    private fun initFragment() {
        val fragmentManager = parentFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        val fragment = ExistMyPlanFragment()
        fragmentTransaction.add(R.id.fcv_purchase_plan, fragment)
        fragmentTransaction.commit()
    }
}