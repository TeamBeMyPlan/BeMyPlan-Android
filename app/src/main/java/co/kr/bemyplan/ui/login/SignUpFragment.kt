package co.kr.bemyplan.ui.login

import android.content.Context.INPUT_METHOD_SERVICE
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import co.kr.bemyplan.R
import co.kr.bemyplan.databinding.FragmentSignUpBinding
import co.kr.bemyplan.ui.main.MainActivity
import co.kr.bemyplan.util.CustomDialog

class SignUpFragment() : Fragment() {
    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding =
            DataBindingUtil.inflate(layoutInflater, R.layout.fragment_sign_up, container, false)

        clickAgree()
        clickStart()
        clickExit()
        closeKeyboard()
        requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        editTextWatcher()
        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun clickAgree() {
        binding.layoutAllAgree.setOnClickListener {
            when (ALL_AGREE) {
                true -> {
                    ALL_AGREE = false
                    TERMS_AGREE = false
                    INFORMATION_AGREE = false
                    with(binding) {
                        ivAllAgree.isSelected = false
                        ivAgreeTerms.isSelected = false
                        ivAgreeInfo.isSelected = false
                    }
                }
                false -> {
                    ALL_AGREE = true
                    TERMS_AGREE = true
                    INFORMATION_AGREE = true
                    with(binding) {
                        ivAllAgree.isSelected = true
                        ivAgreeTerms.isSelected = true
                        ivAgreeInfo.isSelected = true
                    }
                }
            }
            checkStartEnable()
        }

        binding.layoutAgreeTerms.setOnClickListener {
            when (TERMS_AGREE) {
                true -> {
                    TERMS_AGREE = false
                    binding.ivAgreeTerms.isSelected = false
                }
                false -> {
                    TERMS_AGREE = true
                    binding.ivAgreeTerms.isSelected = true
                }
            }
            checkAllAgree()
            checkStartEnable()
        }

        binding.layoutAgreeInfo.setOnClickListener {
            when (INFORMATION_AGREE) {
                true -> {
                    INFORMATION_AGREE = false
                    binding.ivAgreeInfo.isSelected = false
                }
                false -> {
                    INFORMATION_AGREE = true
                    binding.ivAgreeInfo.isSelected = true
                }
            }
            checkAllAgree()
            checkStartEnable()
        }
    }

    private fun checkStartEnable(): Boolean {
        return if (!binding.etNickname.text.isNullOrEmpty() && ALL_AGREE) {
            binding.layoutStart.setBackgroundColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.blue_0077b0
                )
            )
            true
        } else {
            binding.layoutStart.setBackgroundColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.grey_cbd0d7
                )
            )
            false
        }
    }

    private fun checkAllAgree() {
        if (TERMS_AGREE && INFORMATION_AGREE) {
            ALL_AGREE = true
            binding.ivAllAgree.isSelected = true
        } else {
            ALL_AGREE = false
            binding.ivAllAgree.isSelected = false
        }
    }

    private fun clickStart() {
        binding.layoutStart.setOnClickListener {
            if (checkStartCondition()) {
                showDialog()
            }
        }
    }

    private fun checkStartCondition(): Boolean {
        val nickname = binding.etNickname.text.toString()
        if (nickname.length > 20) {
            longNickname()
            return false
        } else if (!ALL_AGREE || nickname.isEmpty()) {
            return false
        }
        return true
    }

    private fun showDialog() {
        val title = "닉네임은 수정할 수 없습니다\n이대로 진행할까요?"
        val dialog = CustomDialog(requireContext(), title)
        dialog.showDialog(R.layout.dialog_yes_no)
        dialog.setOnClickedListener(object : CustomDialog.ButtonClickListener {
            override fun onClicked(num: Int) {
                if (num == 1) {
                    val intent = Intent(requireContext(), MainActivity::class.java)
                    startActivity(intent)
                    requireActivity().finish()
                }
            }
        })
    }

    private fun longNickname() {
        with(binding) {
            etNickname.setBackgroundResource(R.drawable.rectangle_border_red_radius_5)
            tvAlert.text = "특수문자 제외, 20자 이내로 입력해주세요"
        }
    }

    private fun closeKeyboard() {
        binding.root.setOnClickListener {
            val view = requireActivity().currentFocus
            if (view != null) {
                val inputMethodManager: InputMethodManager =
                    requireContext().getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
            }
        }
    }

    private fun editTextWatcher() {
        binding.etNickname.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Text가 바뀌기 전
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Text가 바뀌는 중
            }

            override fun afterTextChanged(s: Editable?) {
                with(binding) {
                    etNickname.setBackgroundResource(R.drawable.rectangle_border_radius_5)
                    tvAlert.text = null
                }
                checkStartEnable()
            }
        })
    }

    private fun clickExit() {
        binding.ivClear.setOnClickListener {
            val title = "회원가입을 그만두시겠습니까?"
            val dialog = CustomDialog(requireContext(), title)
            dialog.showDialog(R.layout.dialog_yes_no)
            dialog.setOnClickedListener(object : CustomDialog.ButtonClickListener {
                override fun onClicked(num: Int) {
                    if (num == 1) {
                        parentFragmentManager.beginTransaction()
                            .remove(this@SignUpFragment)
                            .commit()
                        parentFragmentManager.popBackStack()
                    }
                }
            })
        }
    }

    companion object {
        var ALL_AGREE = false
        var TERMS_AGREE = false
        var INFORMATION_AGREE = false
    }
}