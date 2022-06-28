package co.kr.bemyplan.ui.sort

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import co.kr.bemyplan.R
import co.kr.bemyplan.databinding.DialogSortBinding
import co.kr.bemyplan.ui.list.viewmodel.ListViewModel
import co.kr.bemyplan.ui.main.scrap.viewmodel.ScrapViewModel
import co.kr.bemyplan.ui.sort.viewmodel.SortViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class SortFragment : BottomSheetDialogFragment() {
    private var _binding: DialogSortBinding? = null
    private val binding get() = _binding!!
    private val viewModel by activityViewModels<SortViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, R.layout.dialog_sort, container, false)
        Log.d("mlog: requireActivity() 의 클래스명", requireActivity().localClassName)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initDialog()
        clickLatest()
        clickBestSeller()
//        clickBestScrapper()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun initDialog() {
        (dialog as? BottomSheetDialog)?.behavior?.apply {
            isFitToContents = true
            state = BottomSheetBehavior.STATE_COLLAPSED
        }
        viewModel.sort.observe(viewLifecycleOwner) {
            when (it) {
                "createdAt,desc" -> {
                    binding.tvLatest.setTextColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.blue_0077b0
                        )
                    )
                    binding.tvBestSeller.setTextColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.grey_5d687a
                        )
                    )
//                    binding.tvBestScrapper.setTextColor(
//                        ContextCompat.getColor(
//                            requireContext(),
//                            R.color.grey_5d687a
//                        )
//                    )
                    binding.ivLatest.visibility = View.VISIBLE
                    binding.ivBestSeller.visibility = View.INVISIBLE
//                    binding.ivBestScrapper.visibility = View.INVISIBLE
                }
                "orderCnt,desc" -> {
                    binding.tvLatest.setTextColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.grey_5d687a
                        )
                    )
                    binding.tvBestSeller.setTextColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.blue_0077b0
                        )
                    )
//                    binding.tvBestScrapper.setTextColor(
//                        ContextCompat.getColor(
//                            requireContext(),
//                            R.color.grey_5d687a
//                        )
//                    )
                    binding.ivLatest.visibility = View.INVISIBLE
                    binding.ivBestSeller.visibility = View.VISIBLE
//                    binding.ivBestScrapper.visibility = View.INVISIBLE
                }
//                "createdAt,asc" -> {
//                    binding.tvLatest.setTextColor(
//                        ContextCompat.getColor(
//                            requireContext(),
//                            R.color.grey_5d687a
//                        )
//                    )
//                    binding.tvBestSeller.setTextColor(
//                        ContextCompat.getColor(
//                            requireContext(),
//                            R.color.grey_5d687a
//                        )
//                    )
//                    binding.tvBestScrapper.setTextColor(
//                        ContextCompat.getColor(
//                            requireContext(),
//                            R.color.blue_0077b0
//                        )
//                    )
//                    binding.ivLatest.visibility = View.INVISIBLE
//                    binding.ivBestSeller.visibility = View.INVISIBLE
//                    binding.ivBestScrapper.visibility = View.VISIBLE
//                }
            }
        }
    }

    private fun clickLatest() {
        binding.layoutLatest.setOnClickListener {
            viewModel.setSort(0)
            dialog?.dismiss()
        }
    }

    private fun clickBestSeller() {
        binding.layoutBestSeller.setOnClickListener {
            viewModel.setSort(1)
            dialog?.dismiss()
        }
    }

//    private fun clickBestScrapper() {
//        binding.layoutBestScrapper.setOnClickListener {
//            viewModel.setSort(2)
//            dialog?.dismiss()
//        }
//    }
}