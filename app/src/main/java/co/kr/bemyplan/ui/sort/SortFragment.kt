package co.kr.bemyplan.ui.sort

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import co.kr.bemyplan.R
import co.kr.bemyplan.databinding.DialogSortBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class SortFragment(private val itemClick: (Int) -> Unit) : BottomSheetDialogFragment() {
    private var _binding: DialogSortBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, R.layout.dialog_sort, container, false)
        initDialog()
        clickLatest()
        clickBestSeller()
        clickBestScrapper()
        return binding.root
    }

    private fun initDialog() {
        (dialog as? BottomSheetDialog)?.behavior?.apply {
            isFitToContents = true
            state = BottomSheetBehavior.STATE_COLLAPSED
        }
    }

    private fun clickLatest() {
        binding.layoutLatest.setOnClickListener {
            itemClick(LATEST)
            dialog?.dismiss()
        }
    }

    private fun clickBestSeller() {
        binding.layoutBestSeller.setOnClickListener {
            itemClick(BEST_SELLER)
            dialog?.dismiss()
        }
    }

    private fun clickBestScrapper() {
        binding.layoutBestScrapper.setOnClickListener {
            itemClick(BEST_SCRAPPER)
            dialog?.dismiss()
        }
    }

    companion object {
        const val LATEST = 0
        const val BEST_SELLER = 1
        const val BEST_SCRAPPER = 2
    }
}