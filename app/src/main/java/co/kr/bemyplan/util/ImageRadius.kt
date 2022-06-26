package co.kr.bemyplan.util

import android.annotation.SuppressLint
import android.widget.ImageView
import androidx.exifinterface.media.ExifInterface
import co.kr.bemyplan.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import java.io.IOException

// 이미지 radius 5dp 주는 함수
@SuppressLint("CheckResult")
fun clipTo(view: ImageView, photo: String) {
    val options = RequestOptions().skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE)
    try {
        when(ExifInterface(photo).rotationDegrees) {
            90 -> options.transform(RotateTransform(90f))
            180 -> options.transform(RotateTransform(180f))
            270 -> options.transform(RotateTransform(270f))
        }
    } catch (e: IOException) {
        e.printStackTrace()
    }

    Glide.with(view.context)
        .load(photo)
        .apply(options)
        .error(R.drawable.rectangle_blue_radius_5)
        .into(view)
    view.clipToOutline = true
}