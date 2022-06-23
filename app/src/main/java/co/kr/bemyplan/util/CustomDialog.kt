package co.kr.bemyplan.util

import android.app.Dialog
import android.content.Context
import android.view.WindowManager
import android.widget.TextView
import androidx.annotation.LayoutRes
import co.kr.bemyplan.R

class CustomDialog(val context: Context, val title: String, val content: String) {
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
        dialog.findViewById<TextView>(R.id.tv_content).text = content
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

    fun showChoiceDialogWithTitle(@LayoutRes layout: Int) {
        dialog.setContentView(layout)
        dialog.findViewById<TextView>(R.id.tv_title).text = title
        dialog.findViewById<TextView>(R.id.tv_content).text = content
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
        if(title!="")
            dialog.findViewById<TextView>(R.id.tv_title).text = title
        if(content!="")
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

    fun showLoginDialog() {
        dialog.setContentView(R.layout.dialog_yes_no)
        dialog.findViewById<TextView>(R.id.tv_content).text = "로그인이 필요한 서비스입니다.\n로그인 하시겠습니까?"
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