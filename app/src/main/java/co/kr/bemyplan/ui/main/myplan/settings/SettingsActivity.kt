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

        // 뒤로가기
        binding.ivBack.setOnClickListener {
            finish()
        }

        // 콘텐츠 업로드 신청
        binding.ivUpload.setOnClickListener {
            TODO()
        }

        // 문의하기
        binding.ivQna.setOnClickListener {
            TODO()
        }

        // 이용약관
        binding.ivInfo.setOnClickListener {
            TODO()
        }

        // 로그아웃
        binding.tvLogout.setOnClickListener {
            TODO()
            finish()
        }

        // 회원탈퇴
        binding.tvWithdrawal.setOnClickListener {
            TODO()
        }
    }
}