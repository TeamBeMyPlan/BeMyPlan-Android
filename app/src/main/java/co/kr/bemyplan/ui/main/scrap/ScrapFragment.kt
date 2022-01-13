package co.kr.bemyplan.ui.main.scrap

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import co.kr.bemyplan.R
import co.kr.bemyplan.databinding.FragmentScrapBinding

class ScrapFragment : Fragment() {
    private var _binding: FragmentScrapBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_scrap, container, false)
        initFragmentContainerView()
        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun initFragmentContainerView() {
        // 스크랩한 글이 있을 때
//        val transaction = childFragmentManager.beginTransaction()
//        transaction.add(R.id.fcv_scrap, NotEmptyScrapFragment())
//            .commit()

        // 스크랩한 글이 없을 때
        val transaction = childFragmentManager.beginTransaction()
        transaction.add(R.id.fcv_scrap, EmptyScrapFragment())
            .commit()
    }
}