package co.kr.bemyplan.ui.purchase.after

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import co.kr.bemyplan.databinding.ActivityAfterPurchaseBinding
import net.daum.mf.map.api.MapView

class AfterPurchaseActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAfterPurchaseBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAfterPurchaseBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun setMap() {
        val mapView = MapView(this)
        binding.mapView.addView(mapView)
    }
}