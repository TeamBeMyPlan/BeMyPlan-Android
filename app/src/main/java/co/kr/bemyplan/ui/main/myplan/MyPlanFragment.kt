package co.kr.bemyplan.ui.main.myplan

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import co.kr.bemyplan.R
import co.kr.bemyplan.data.myplan.Profile
import co.kr.bemyplan.databinding.FragmentMyPlanBinding
import com.bumptech.glide.Glide

class MyPlanFragment : Fragment() {
    private var _binding: FragmentMyPlanBinding? = null
    private val binding get() = _binding!!
    //private lateinit var profile: Profile

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMyPlanBinding.inflate(layoutInflater, container, false)
        initProfile()
        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    fun initProfile() {
//        profile = Profile("다운타운베이비", R.drawable.ic_img_profile, 0)
    }
}