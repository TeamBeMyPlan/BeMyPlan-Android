package co.kr.bemyplan.ui.login

import co.kr.bemyplan.R
import co.kr.bemyplan.databinding.ActivityLoginBinding
import co.kr.bemyplan.ui.base.BaseActivity
import co.kr.bemyplan.util.CustomDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : BaseActivity<ActivityLoginBinding>(R.layout.activity_login) {
    override fun onBackPressed() {
        val fragment = supportFragmentManager.findFragmentByTag("signUpFragment")
        if (fragment != null && fragment.isVisible) {
            if (fragment.childFragmentManager.backStackEntryCount == 0) {
                showExitDialog()
            } else {
                fragment.childFragmentManager.popBackStackImmediate()
            }
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