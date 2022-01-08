package co.kr.bemyplan.ui.purchase.after

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import co.kr.bemyplan.R
import co.kr.bemyplan.databinding.FragmentDailyContentsBinding

class DailyContentsFragment : Fragment() {
    private lateinit var binding: FragmentDailyContentsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)
        binding = FragmentDailyContentsBinding.inflate(layoutInflater)

        return binding.root
    }
}