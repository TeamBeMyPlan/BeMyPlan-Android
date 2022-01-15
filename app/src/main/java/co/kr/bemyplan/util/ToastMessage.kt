package co.kr.bemyplan.util

import android.content.Context
import android.widget.Toast

object ToastMessage {
    fun Context.shortToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
    fun Context.longToast(message : String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}