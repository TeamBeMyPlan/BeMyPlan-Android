package co.kr.bemyplan.ui.main.myplan.settings.withdrawal

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.content.res.ColorStateList
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Base64
import android.util.Log
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import co.kr.bemyplan.R
import co.kr.bemyplan.databinding.ActivityWithdrawalBinding
import com.kakao.util.maps.helper.Utility
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

    private val watcherListener = object: TextWatcher {
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
        binding.tvNextButton.isClickable = binding.etWithdrawal.text.toString().isNotEmpty()
    }

    @SuppressLint("ResourceAsColor")
    private fun buttonEvent(statement: Boolean) {
        if(statement) {
            binding.tvNextButton.setBackgroundResource(R.drawable.rectangle_blue_radius_5)
        }
        else {
            binding.tvNextButton.setBackgroundResource(R.drawable.rectangle_grey_radius_5)
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initTouchListener() {
        binding.etWithdrawal.setOnTouchListener { _, _ ->
            binding.sv.requestDisallowInterceptTouchEvent(true)
            false
        }
    }
}