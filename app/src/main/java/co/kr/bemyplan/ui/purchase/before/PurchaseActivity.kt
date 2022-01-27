package co.kr.bemyplan.ui.purchase.before

import android.os.Bundle
import android.util.Log
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
        var postId = intent.getIntExtra("postId", -1)
        var isScraped = intent.getBooleanExtra("isScraped", false)
        Log.d("mlog: PurchaseActivity::getExtra에서 postId", postId.toString())
        Log.d("mlog: PurchaseActivity::getExtra에서 isScraped", isScraped.toString())
        viewModel.setPostId(postId)
        viewModel.setIsScraped(isScraped)
        binding = ActivityPurchaseBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}