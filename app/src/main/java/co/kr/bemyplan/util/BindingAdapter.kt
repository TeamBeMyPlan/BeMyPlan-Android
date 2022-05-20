package co.kr.bemyplan.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import co.kr.bemyplan.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions

object BindingAdapter {
    private val multiOption = MultiTransformation(CenterCrop(), RoundedCorners(5.dpToPx()))

    @JvmStatic
    @BindingAdapter("setImage")
    fun setImage(view: ImageView, src: Int) {
        view.setImageResource(src)
    }

    @JvmStatic
    @BindingAdapter("setImageString")
    fun setImageString(view: ImageView, src: String?) {
        if (src != null) {
            Glide.with(view.context)
                .load(src)
                .apply(RequestOptions.bitmapTransform(multiOption))
                .error(R.drawable.rectangle_grey_radius_5)
                .into(view)
        }
    }
}