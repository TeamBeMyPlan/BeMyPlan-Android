package co.kr.bemyplan.ui.purchase.before

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import co.kr.bemyplan.R

class ZeroEventDialog : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val inflater = requireActivity().layoutInflater
            builder.setView(inflater.inflate(R.layout.dialog_zero_event, null))
                .setPositiveButton("확인") { _, _ ->
                    val transaction = parentFragmentManager.beginTransaction()
                    val chargedFragment = ChargedFragment()
                    transaction.replace(R.id.fragment_container_charging, chargedFragment)
                    transaction.commit()
                }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}