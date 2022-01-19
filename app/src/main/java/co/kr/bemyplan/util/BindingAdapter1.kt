package co.kr.bemyplan.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

object BindingAdapter1 {
    @JvmStatic
    @BindingAdapter("setImage")
    fun setImage(view: ImageView, src: Int) {
        view.setImageResource(src)
    }

    @JvmStatic
    @BindingAdapter("setImageString")
    fun setImageString(view: ImageView, src: String?) {
        if(src != null) {
            Glide.with(view.context)
                .load(src)
                .into(view)
        }
    }
}