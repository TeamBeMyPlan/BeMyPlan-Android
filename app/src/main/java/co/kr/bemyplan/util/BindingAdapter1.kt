package co.kr.bemyplan.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import co.kr.bemyplan.R
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
                .placeholder(R.drawable.rectangle_grey_radius_5)
                .into(view)
        }
    }
}