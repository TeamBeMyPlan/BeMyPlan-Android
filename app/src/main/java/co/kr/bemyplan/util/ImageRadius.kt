package co.kr.bemyplan.util

import android.widget.ImageView
import co.kr.bemyplan.R
import com.bumptech.glide.Glide

// 이미지 radius 5dp 주는 함수
fun clipTo(view: ImageView, photo: String) {
    Glide.with(view.context)
        .load(photo)
        .error(R.drawable.rectangle_blue_radius_5)
        .into(view)
    view.clipToOutline = true
}