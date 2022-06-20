package co.kr.bemyplan.data.firebase

import android.os.Bundle
import co.kr.bemyplan.data.local.BeMyPlanDataStore
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import javax.inject.Inject

class FirebaseAnalyticsProvider() {
    @Inject
    lateinit var beMyPlanDataStore: BeMyPlanDataStore

    val parameters = Bundle().apply {
        this.putString("device", "Android")
        this.putInt("userId", beMyPlanDataStore.userId)
    }
    val firebaseAnalytics = Firebase.analytics.apply {
        setDefaultEventParameters(parameters)
    }
}