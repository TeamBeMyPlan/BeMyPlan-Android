package co.kr.bemyplan.ui.main.myplan.settings.withdrawal

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MotionEvent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import co.kr.bemyplan.R
import co.kr.bemyplan.databinding.ActivityWithdrawalBinding
import co.kr.bemyplan.ui.login.LoginActivity
import co.kr.bemyplan.util.CustomDialog
import co.kr.bemyplan.util.ToastMessage.shortToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WithdrawalActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWithdrawalBinding
    private val viewModel by viewModels<WithdrawalViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWithdrawalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
    }

    private fun initView() {
        binding.ivBack.setOnClickListener {
            finish()
        }

        binding.etWithdrawal.addTextChangedListener(watcherListener)
        initTouchListener()
    }

    private val watcherListener = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            toggleButtonClickable()
            buttonEvent(binding.tvNextButton.isClickable)
        }

        override fun afterTextChanged(p0: Editable?) {
        }
    }

    private fun toggleButtonClickable() {
        binding.tvNextButton.isClickable = false
        if (binding.etWithdrawal.text.toString().isNotEmpty()) {
            binding.tvNextButton.isClickable = true
            binding.tvNextButton.setOnClickListener {
                showWithdrawalDialog()
            }
        }
    }

    @SuppressLint("ResourceAsColor")
    private fun buttonEvent(statement: Boolean) {
        if (statement) {
            binding.tvNextButton.setBackgroundResource(R.drawable.rectangle_blue_radius_5)
        } else {
            binding.tvNextButton.setBackgroundResource(R.drawable.rectangle_grey_radius_5)
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initTouchListener() {
        binding.etWithdrawal.setOnTouchListener { view, event ->
            if (binding.etWithdrawal.hasFocus()) {
                view.parent.requestDisallowInterceptTouchEvent(true)
                when (event.action) {
                    MotionEvent.ACTION_SCROLL -> {
                        view.parent.requestDisallowInterceptTouchEvent(false)
                        true
                    }
                }
            }
            false
        }
    }

    private fun showWithdrawalDialog() {
        val content = "정말 탈퇴하시겠습니까?"
        val dialog = CustomDialog(this, "탈퇴하기", content)
        dialog.showChoiceDialogWithTitle(R.layout.dialog_yes_no_with_title)
        dialog.setOnClickedListener(object : CustomDialog.ButtonClickListener {
            override fun onClicked(num: Int) {
                if (num == 1) {
                    runCatching {
                        viewModel.signOut(binding.etWithdrawal.text.toString())
                    }.onSuccess {
                        showWithdrawalFinishedDialog()
                    }.onFailure {
                        this@WithdrawalActivity.shortToast("문제가 발생했습니다. 잠시 후 다시 시도해주세요.")
                    }
                }
            }
        })
    }

    private fun showWithdrawalFinishedDialog() {
        val content = "탈퇴 완료되었습니다."
        val dialog = CustomDialog(this, "탈퇴하기", content)
        dialog.showConfirmDialog(R.layout.dialog_yes)
        dialog.setOnClickedListener(object : CustomDialog.ButtonClickListener {
            override fun onClicked(num: Int) {
                if (num == 1) {
                    val intent = Intent(this@WithdrawalActivity, LoginActivity::class.java).apply {
                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    }
                    startActivity(intent)
                    this@WithdrawalActivity.finish()
                }
            }
        })
    }
}