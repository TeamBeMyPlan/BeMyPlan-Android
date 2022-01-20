package co.kr.bemyplan.ui.main.myplan.settings.creator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import co.kr.bemyplan.databinding.ActivityCreatorProposeBinding

class CreatorProposeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCreatorProposeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreatorProposeBinding.inflate(layoutInflater)

        initBackBtn()

        setContentView(binding.root)
    }

    private fun initBackBtn() {
        binding.ivBack.setOnClickListener {
            finish()
        }
    }
}