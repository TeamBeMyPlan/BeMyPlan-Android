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
        setValue() // note: View 생성 전에 value 할당해야 한다. 그렇지 않으면 하위 프래그먼트 생성 시 할당되지 않은 value 로 서버통신하게 됨
        binding = ActivityPurchaseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        observeLiveData()
    }

    private fun setValue() {
        val planId = intent.getIntExtra("planId", -1)
        val scrapStatus = intent.getBooleanExtra("scrapStatus", false)
        val authorNickname = intent.getStringExtra("authorNickname") ?: ""
        val authorUserId = intent.getIntExtra("authorUserId", -1)
        val thumbnail = intent.getStringExtra("thumbnail") ?: ""

        viewModel.setPlanId(planId)
        viewModel.setScrapStatus(scrapStatus)
        viewModel.setAuthor(authorNickname, authorUserId)
        viewModel.setThumbnail(thumbnail)
    }

    private fun observeLiveData() {
        viewModel.scrapStatus.observe(this) {
            intent.putExtra("scrapStatus", it)
            intent.putExtra("planId", requireNotNull(viewModel.planId))
            setResult(RESULT_OK, intent)
        }
    }
}