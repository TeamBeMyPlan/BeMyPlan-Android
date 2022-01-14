package co.kr.bemyplan.ui.main.myplan.settings

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import co.kr.bemyplan.R
import co.kr.bemyplan.databinding.ActivitySettingsBinding

class SettingsActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}