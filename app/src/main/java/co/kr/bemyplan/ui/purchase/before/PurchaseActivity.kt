package co.kr.bemyplan.ui.purchase.before

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import co.kr.bemyplan.databinding.ActivityPurchaseBinding
import co.kr.bemyplan.ui.purchase.before.viewmodel.BeforeChargingViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PurchaseActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPurchaseBinding
    private val viewModel by viewModels<BeforeChargingViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val planId = intent.getIntExtra("planId", -1)
        val scrapStatus = intent.getBooleanExtra("scrapStatus", false)
        val authorNickname = intent.getStringExtra("authorNickname") ?: ""
        val authorUserId = intent.getIntExtra("authorUserId", -1)

        viewModel.setPlanId(planId)
        viewModel.setScrapStatus(scrapStatus)
        viewModel.setAuthor(authorNickname, authorUserId)

        binding = ActivityPurchaseBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}