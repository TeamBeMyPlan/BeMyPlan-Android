package co.kr.bemyplan.ui.login

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import co.kr.bemyplan.R
import co.kr.bemyplan.databinding.ActivityLoginBinding
import co.kr.bemyplan.util.CustomDialog
import com.kakao.util.maps.helper.Utility

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
    }

    override fun onBackPressed() {
        val fragment = supportFragmentManager.findFragmentByTag("signUpFragment")
        if(fragment != null && fragment.isVisible) {
            showExitDialog()
        } else {
            super.onBackPressed()
        }
    }

    private fun showExitDialog() {
        val content = "회원가입을 그만두시겠습니까?"
        val dialog = CustomDialog(this, "", content)
        dialog.showChoiceDialog(R.layout.dialog_yes_no)
        dialog.setOnClickedListener(object : CustomDialog.ButtonClickListener {
            override fun onClicked(num: Int) {
                if (num == 1) {
                    supportFragmentManager.findFragmentByTag("signUpFragment")?.let {
                        supportFragmentManager.beginTransaction()
                            .remove(it)
                            .commit()
                    }
                    supportFragmentManager.popBackStack()
                }
            }
        })
    }
}