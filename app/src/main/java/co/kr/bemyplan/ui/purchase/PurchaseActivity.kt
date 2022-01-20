package co.kr.bemyplan.ui.purchase

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import co.kr.bemyplan.databinding.ActivityPurchaseBinding
import co.kr.bemyplan.ui.purchase.before.viewmodel.BeforeChargingViewModel

class PurchaseActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPurchaseBinding
    private val viewModel by viewModels<BeforeChargingViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val postId = intent.getIntExtra("postId", -1)
        Log.d("mlog: postId", postId.toString())
        viewModel.setPostId(postId)
        binding = ActivityPurchaseBinding.inflate(layoutInflater)

        setContentView(binding.root)
    }
}