package co.kr.bemyplan.ui.main.scrap

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import co.kr.bemyplan.R
import co.kr.bemyplan.databinding.FragmentScrapBinding
import co.kr.bemyplan.ui.list.viewmodel.ListViewModel

class ScrapFragment : Fragment() {
    private var _binding: FragmentScrapBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<ListViewModel>()

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
        viewModel.getScrapList()
        viewModel.scrapList.observe(viewLifecycleOwner) {
            Log.d("mlog: ScrapFragment.initFragmentContainerView", "execute")
            when (it.size) {
                0 -> {
                    val transaction = childFragmentManager.beginTransaction()
                    transaction.add(R.id.fcv_scrap, EmptyScrapFragment())
                        .commit()
                }
                else -> {
                    val transaction = childFragmentManager.beginTransaction()
                    transaction.add(R.id.fcv_scrap, NotEmptyScrapFragment())
                        .commit()
                }
            }
        }
    }
}