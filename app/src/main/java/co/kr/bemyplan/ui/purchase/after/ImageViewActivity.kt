package co.kr.bemyplan.ui.purchase.after

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import co.kr.bemyplan.databinding.ActivityAfterPurchaseBinding
import co.kr.bemyplan.databinding.ActivityImageViewBinding
import co.kr.bemyplan.databinding.ActivityPurchaseBinding

class ImageViewActivity : AppCompatActivity() {
    private lateinit var binding: ActivityImageViewBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityImageViewBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}