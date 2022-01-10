package co.kr.bemyplan.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import co.kr.bemyplan.R
import co.kr.bemyplan.databinding.FragmentScrapBinding
import co.kr.bemyplan.ui.purchase.after.AfterPurchaseActivity

// 작업을 위한 임시용 코드
class ScrapFragment : Fragment() {
    private lateinit var binding: FragmentScrapBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)
        binding = FragmentScrapBinding.inflate(layoutInflater)

        binding.btnSample.setOnClickListener {
            startActivity(Intent(activity, AfterPurchaseActivity::class.java))
        }

        return binding.root
    }
}