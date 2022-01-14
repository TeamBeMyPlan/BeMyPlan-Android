package co.kr.bemyplan.util

import android.widget.ImageView
import com.bumptech.glide.Glide

// 이미지 radius 5dp 주는 함
fun clipTo(view: ImageView, photo: String) {
    Glide.with(view.context)
        .load(photo)
        .into(view)
    view.clipToOutline = true
}