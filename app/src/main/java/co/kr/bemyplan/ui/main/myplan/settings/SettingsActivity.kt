package co.kr.bemyplan.ui.main.myplan.settings

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import co.kr.bemyplan.R
import co.kr.bemyplan.data.local.AutoLoginData
import co.kr.bemyplan.data.local.BeMyPlanDataStore
import co.kr.bemyplan.databinding.ActivitySettingsBinding
import co.kr.bemyplan.ui.login.LoginActivity
import co.kr.bemyplan.ui.main.MainActivity
import co.kr.bemyplan.ui.main.myplan.settings.creator.CreatorProposeActivity
import co.kr.bemyplan.ui.main.myplan.settings.viewmodel.SettingsViewModel
import co.kr.bemyplan.ui.main.myplan.settings.withdrawal.WithdrawalActivity
import co.kr.bemyplan.util.CustomDialog
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.kakao.sdk.auth.AuthApi
import com.kakao.sdk.user.UserApiClient
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SettingsActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingsBinding
    @Inject
    lateinit var dataStore: BeMyPlanDataStore
    var googleSignInClient : GoogleSignInClient ?= null

    private val viewModel by viewModels<SettingsViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 뒤로가기
        binding.clBack.setOnClickListener {
            finish()
        }

        // 콘텐츠 업로드 신청
        binding.clUpload.setOnClickListener {
            showProposeContentsUpload()
        }

        // 문의하기
        binding.clQna.setOnClickListener {
            showQna()
        }

        // 이용약관
        binding.clInfo.setOnClickListener {
            showInfo()
        }

        // 로그아웃
        binding.clLogout.setOnClickListener {
            showLogoutDialog()
        }

        // 회원탈퇴
        binding.clWithdrawal.setOnClickListener {
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
                    dataStore.sessionId = ""
                    dataStore.userId = 0
                    showLogoutFinishedDialog()
                }
            }
        })
    }

    private fun showLogoutFinishedDialog() {
        val content = "로그아웃 되었습니다."
        val dialog = CustomDialog(this, "로그아웃", content)
        dialog.showConfirmDialog(R.layout.dialog_yes)
        dialog.setOnClickedListener(object : CustomDialog.ButtonClickListener {
            override fun onClicked(num: Int) {
                if (num == 1) {
//                    // 구글 로그아웃을 위해 로그인 세션 가져오기
//                    var gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                        .requestIdToken(getString(R.string.default_web_client_id))
//                        .requestEmail()
//                        .build()
//                    googleSignInClient = GoogleSignIn.getClient(this@SettingsActivity, gso)
                    UserApiClient.instance.logout {
                        val intent = Intent(this@SettingsActivity, LoginActivity::class.java)
                        startActivity(intent)
                        this@SettingsActivity.finish()
                    }
                    googleSignInClient
                }

            }
        })
    }

    private fun showProposeContentsUpload() {
        val intent = Intent(this, CreatorProposeActivity::class.java)
        startActivity(intent)
    }

    private fun showQna() {
        val email = Intent(Intent.ACTION_SEND)
        email.type = "plain/text"
        val address = arrayOf("teambemyplan@gmail.com")
        email.putExtra(Intent.EXTRA_EMAIL, address)
        startActivity(email)
    }

    private fun showInfo() {
        val intent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse("https://www.notion.so/a69b7abcdb9f42399825f4ff25343bfd")
        )
        startActivity(intent)
    }
}