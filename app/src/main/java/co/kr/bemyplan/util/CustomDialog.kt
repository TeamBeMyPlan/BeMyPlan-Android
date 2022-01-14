package co.kr.bemyplan.util

import android.app.Dialog
import android.content.Context
import android.view.WindowManager
import android.widget.TextView
import androidx.annotation.LayoutRes
import co.kr.bemyplan.R

class CustomDialog(context: Context, val title: String, val content: String) {
    private val dialog = Dialog(context)
    private lateinit var onClickedListener: ButtonClickListener

    interface ButtonClickListener {
        fun onClicked(num: Int)
    }

    fun setOnClickedListener(listener: ButtonClickListener) {
        onClickedListener = listener
    }

    fun showChoiceDialog(@LayoutRes layout: Int) {
        dialog.setContentView(layout)
        dialog.findViewById<TextView>(R.id.tv_title).text = content
        dialog.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        dialog.window?.setBackgroundDrawableResource(R.drawable.inset_horizontal_24)
        dialog.show()

        dialog.findViewById<TextView>(R.id.tv_no).setOnClickListener {
            dialog.dismiss()
        }
        dialog.findViewById<TextView>(R.id.tv_yes).setOnClickListener {
            onClickedListener.onClicked(1)
            dialog.dismiss()
        }
    }

    fun showConfirmDialog(@LayoutRes layout: Int) {
        dialog.setContentView(layout)
        dialog.findViewById<TextView>(R.id.tv_title).text = title
        dialog.findViewById<TextView>(R.id.tv_content).text = content
        dialog.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        dialog.window?.setBackgroundDrawableResource(R.drawable.inset_horizontal_24)
        dialog.show()

        dialog.findViewById<TextView>(R.id.tv_yes).setOnClickListener {
            onClickedListener.onClicked(1)
            dialog.dismiss()
        }
    }
}