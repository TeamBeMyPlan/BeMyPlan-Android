package co.kr.bemyplan.util

import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter

object BindingAdapter1 {
    @JvmStatic
    @BindingAdapter("setImage")
    fun setImage(view: ImageView, src: Int) {
        view.setImageResource(src)
    }
}