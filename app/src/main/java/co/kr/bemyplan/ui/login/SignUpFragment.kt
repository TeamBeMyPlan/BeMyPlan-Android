package co.kr.bemyplan.ui.login

import android.content.Context.INPUT_METHOD_SERVICE
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import co.kr.bemyplan.R
import co.kr.bemyplan.databinding.FragmentSignUpBinding
import co.kr.bemyplan.ui.login.viewmodel.LoginViewModel
import co.kr.bemyplan.ui.main.MainActivity
import co.kr.bemyplan.util.CustomDialog

class SignUpFragment : Fragment() {
    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!
    private val viewModel by activityViewModels<LoginViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding =
            DataBindingUtil.inflate(layoutInflater, R.layout.fragment_sign_up, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeNickname()
        showSignUpDialog()
        clickExit()
        closeKeyboard()
    }

    private fun observeNickname() {
        viewModel.nickname.observe(viewLifecycleOwner, {
            Log.d("mlog: nickname", viewModel.nickname.value.toString())
            viewModel.setIsDuplicatedNull()
            viewModel.checkIsValid()
        })
    }

    private fun showSignUpDialog() {
        viewModel.signUpPermission.observe(viewLifecycleOwner) {
            val content = resources.getString(R.string.text_keep_login)
            val dialog = CustomDialog(requireContext(), "", content)
            dialog.showChoiceDialog(R.layout.dialog_yes_no)
            dialog.setOnClickedListener(object : CustomDialog.ButtonClickListener {
                override fun onClicked(num: Int) {
                    if (num == 1) {
                        // socialToken, loginType, nickname을 서버에 넘겨주는 코드 작성하는 부분
                        Log.d("mlog: nickname.value", viewModel.nickname.value.toString())
                        Log.d("mlog: loginType.value", viewModel.loginType.value.toString())
                        val intent = Intent(requireContext(), MainActivity::class.java)
                        startActivity(intent)
                        requireActivity().finish()
                    }
                }
            })
        }
    }

    private fun clickExit() {
        binding.ivClear.setOnClickListener {
            showExitDialog()
        }
    }

    private fun showExitDialog() {
        val content = "회원가입을 그만두시겠습니까?"
        val dialog = CustomDialog(requireContext(), "", content)
        dialog.showChoiceDialog(R.layout.dialog_yes_no)
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
}