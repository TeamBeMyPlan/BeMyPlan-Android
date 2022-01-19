package co.kr.bemyplan.ui.main.scrap

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import co.kr.bemyplan.R
import co.kr.bemyplan.databinding.FragmentScrapBinding
import co.kr.bemyplan.ui.list.viewmodel.ListViewModel
import co.kr.bemyplan.ui.main.scrap.viewmodel.ScrapViewModel

class ScrapFragment : Fragment() {
    private var _binding: FragmentScrapBinding? = null
    private val binding get() = _binding!!
    private val viewModel by activityViewModels<ListViewModel>()

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
        viewModel.getScrapList("1")
        viewModel.scrapList.observe(viewLifecycleOwner) {
            when(it.size) {
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