package co.kr.bemyplan.util

import android.annotation.SuppressLint
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import co.kr.bemyplan.R
import com.bumptech.glide.Glide

object BindingAdapter {
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
                .placeholder(R.drawable.rectangle_grey_radius_5)
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