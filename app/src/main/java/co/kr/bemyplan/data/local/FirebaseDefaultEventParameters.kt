package co.kr.bemyplan.data.local

import android.os.Bundle

object FirebaseDefaultEventParameters {
    val parameters = Bundle().apply {
        this.putString("device", "Android")
        this.putInt("userIdx", 990101)
    }
}