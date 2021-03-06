package co.kr.bemyplan.ui.main.scrap

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import co.kr.bemyplan.R
import co.kr.bemyplan.databinding.FragmentScrapBinding
import co.kr.bemyplan.ui.main.scrap.viewmodel.ScrapViewModel
import co.kr.bemyplan.ui.sort.viewmodel.SortViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ScrapFragment : Fragment() {
    private var _binding: FragmentScrapBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<ScrapViewModel>()
    private val sortViewModel by activityViewModels<SortViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_scrap, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initFragmentContainerView()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun initFragmentContainerView() {
        viewModel.getScrapList(sortViewModel.sort.value.toString())
        viewModel.scrapList.observe(viewLifecycleOwner) {
            when (it.size) {
                0 -> {
                    val transaction = childFragmentManager.beginTransaction()
                    transaction.replace(R.id.fcv_scrap, EmptyScrapFragment())
                        .commit()
                }
                else -> {
                    val transaction = childFragmentManager.beginTransaction()
                    transaction.replace(R.id.fcv_scrap, NotEmptyScrapFragment())
                        .commit()
                }
            }
        }
    }
}