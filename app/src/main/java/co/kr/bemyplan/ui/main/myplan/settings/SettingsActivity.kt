package co.kr.bemyplan.ui.main.myplan.settings

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.content.ContentProviderCompat.requireContext
import co.kr.bemyplan.R
import co.kr.bemyplan.databinding.ActivitySettingsBinding
import co.kr.bemyplan.ui.login.LoginActivity
import co.kr.bemyplan.ui.main.MainActivity
import co.kr.bemyplan.ui.main.myplan.settings.withdrawal.WithdrawalActivity
import co.kr.bemyplan.util.CustomDialog

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
            showInfo()
        }

        // 로그아웃
        binding.tvLogout.setOnClickListener {
            showLogoutDialog()
        }

        // 회원탈퇴
        binding.tvWithdrawal.setOnClickListener {
            val intent = Intent(this, WithdrawalActivity::class.java)
            startActivity(intent)
        }
    }

    private fun showLogoutDialog() {
        val content = "로그아웃 하시겠습니까?"
        val dialog = CustomDialog(this, "로그아웃", content)
        dialog.showChoiceDialogWithTitle(R.layout.dialog_yes_no_with_title)
        dialog.setOnClickedListener(object : CustomDialog.ButtonClickListener {
            override fun onClicked(num: Int) {
                if (num == 1) {
                    showLogoutFinishedDialog()
                }
            }
        })
    }

    private fun showLogoutFinishedDialog() {
        val content = "로그아웃 되었습니다."
        val dialog = CustomDialog(this, "로그아웃", content)
        dialog.showConfirmDialog(R.layout.dialog_yes)
        dialog.setOnClickedListener(object: CustomDialog.ButtonClickListener {
            override fun onClicked(num: Int) {
                if (num == 1) {
                    val intent = Intent(this@SettingsActivity, LoginActivity::class.java)
                    startActivity(intent)
                    this@SettingsActivity.finish()
                }
            }
        })
   }

    private fun showInfo() {
        binding.tvInfo.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.notion.so/a69b7abcdb9f42399825f4ff25343bfd"))
            startActivity(intent)
        }
    }
}