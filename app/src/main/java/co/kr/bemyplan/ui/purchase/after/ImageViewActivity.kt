package co.kr.bemyplan.ui.purchase.after

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import co.kr.bemyplan.databinding.ActivityImageViewBinding

class ImageViewActivity : AppCompatActivity() {
    private lateinit var binding: ActivityImageViewBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityImageViewBinding.inflate(layoutInflater)
        binding.photo = intent.getStringExtra("photoUrl")
        initBackButton()

        setContentView(binding.root)
    }

    private fun initBackButton() {
        binding.ivBack.setOnClickListener {
            finish()
        }
    }
}