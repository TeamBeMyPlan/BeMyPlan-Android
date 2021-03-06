package co.kr.bemyplan.util

import android.annotation.SuppressLint
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.exifinterface.media.ExifInterface
import co.kr.bemyplan.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import java.io.IOException

object BindingAdapter {
    private val multiOption = MultiTransformation(CenterCrop(), RoundedCorners(5.dpToPx()))
    private val options = RequestOptions().skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE)

    @JvmStatic
    @BindingAdapter("setImage")
    fun setImage(view: ImageView, src: Int) {
        view.setImageResource(src)
    }

    @JvmStatic
    @BindingAdapter("setImageString")
    fun setImageString(view: ImageView, src: String?) {
        if (src != null) {
            try {
                when (ExifInterface(src).rotationDegrees) {
                    90 -> options.transform(RotateTransform(90f))
                    180 -> options.transform(RotateTransform(180f))
                    270 -> options.transform(RotateTransform(270f))
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }

            Glide.with(view.context)
                .load(src)
                .apply(RequestOptions.bitmapTransform(multiOption))
                .apply(options)
                .error(R.drawable.rectangle_grey_radius_5)
                .into(view)
        }
    }

    @SuppressLint("SetTextI18n")
    @JvmStatic
    @BindingAdapter(value = ["mobility, spentMinute"], requireAll = false)
    fun TextView.setSpentMinute(mobility: String, spentMinute: String) {
        when(mobility) {
            "CAR" -> {
                text = "차량 $spentMinute"
            }
            "BICYCLE" -> {
                text = "자전거 $spentMinute"
            }
            "PUBLIC" -> {
                text = "대중교통 $spentMinute"
            }
            "WALK" -> {
                text = "도보 $spentMinute"
            }
            else -> {text = "교통수단이 없습니다."}
        }
    }
}