package co.kr.bemyplan.util

import android.content.res.Resources

fun Int.dpToPx(): Int = this * Resources.getSystem().displayMetrics.density.toInt()