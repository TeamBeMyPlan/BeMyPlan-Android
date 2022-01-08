package co.kr.bemyplan.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter

object BindingAdapter1 {
    @JvmStatic
    @BindingAdapter("setImage")
    fun setImage(view: ImageView, src: Int) {
        view.setImageResource(src)
    }
}