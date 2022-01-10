package co.kr.bemyplan.ui.purchase.after.adapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter

object ImageBindingAdapter {
    @JvmStatic
    @BindingAdapter("res")
    fun setImage(imageView: ImageView, res: Int) {
        imageView.setImageResource(res)
    }
}