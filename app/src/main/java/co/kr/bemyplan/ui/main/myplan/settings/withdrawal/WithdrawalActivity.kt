package co.kr.bemyplan.ui.main.myplan.settings.withdrawal

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.ColorStateList
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Base64
import android.util.Log
import android.view.MotionEvent
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import co.kr.bemyplan.R
import co.kr.bemyplan.data.api.ApiService
import co.kr.bemyplan.data.entity.main.myplan.RequestWithdraw
import co.kr.bemyplan.data.entity.main.myplan.ResponseWithdraw
import co.kr.bemyplan.databinding.ActivityWithdrawalBinding
import co.kr.bemyplan.ui.login.LoginActivity
import co.kr.bemyplan.util.CustomDialog
import com.kakao.util.maps.helper.Utility
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.security.MessageDigest

class WithdrawalActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWithdrawalBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWithdrawalBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
                    initNetwork()
                    showWithdrawalFinishedDialog()
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
                    val intent = Intent(this@WithdrawalActivity, LoginActivity::class.java)
                    startActivity(intent)
                    this@WithdrawalActivity.finish()
                }
            }
        })
    }

    private fun initNetwork() {
        val reason = binding.etWithdrawal.text.toString()
        val call = ApiService.withdrawService.deleteToken(body = RequestWithdraw(reason))
        call.enqueue(object : Callback<ResponseWithdraw> {
            override fun onResponse(
                call: Call<ResponseWithdraw>,
                response: Response<ResponseWithdraw>
            ) {
                if (response.isSuccessful) {
                    //TODO: 삭제
                }
            }

            override fun onFailure(call: Call<ResponseWithdraw>, t: Throwable) {
                Log.d("탈퇴 서버 통신 실패", t.message!!)
            }
        })
    }
}