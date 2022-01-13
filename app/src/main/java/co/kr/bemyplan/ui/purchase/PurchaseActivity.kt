package co.kr.bemyplan.ui.purchase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import co.kr.bemyplan.R
import co.kr.bemyplan.databinding.ActivityPurchaseBinding
import co.kr.bemyplan.ui.purchase.before.BeforeChargingFragment

class PurchaseActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPurchaseBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPurchaseBinding.inflate(layoutInflater)

        setContentView(binding.root)
    }
}