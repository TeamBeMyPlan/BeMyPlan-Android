package co.kr.bemyplan.ui.login.signup

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import co.kr.bemyplan.R
import co.kr.bemyplan.databinding.FragmentCheckNicknameBinding
import co.kr.bemyplan.ui.login.viewmodel.LoginViewModel
import co.kr.bemyplan.util.CustomDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CheckNicknameFragment : Fragment() {
    private var _binding: FragmentCheckNicknameBinding? = null
    private val binding get() = _binding ?: error("binding not initialized")
    private val viewModel by activityViewModels<LoginViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_check_nickname, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeNickname()
        showNextDialog()
        clickExit()
        closeKeyboard()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun observeNickname() {
        viewModel.nickname.observe(viewLifecycleOwner) {
            Log.d("mlog: nickname", viewModel.nickname.value.toString())
            viewModel.setIsDuplicatedNicknameNull()
            viewModel.checkIsValidNickname()
        }
    }

    private fun clickExit() {
        binding.layoutBack.setOnClickListener {
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
                        .remove(this@CheckNicknameFragment)
                        .commit()
                    parentFragmentManager.popBackStack()
                }
            }
        })
    }

    private fun showNextDialog() {
        viewModel.nicknamePermission.observe(viewLifecycleOwner) {
            val content = resources.getString(R.string.text_keep_login)
            val dialog = CustomDialog(requireContext(), "", content)
            dialog.showChoiceDialog(R.layout.dialog_yes_no)
            dialog.setOnClickedListener(object : CustomDialog.ButtonClickListener {
                override fun onClicked(num: Int) {
                    if (num == 1) {
                        val transaction = parentFragmentManager.beginTransaction()
                        transaction.replace(R.id.fcv_sign_up, CheckEmailFragment())
                            .addToBackStack(null)
                            .commit()
                    }
                }
            })
        }
    }

    private fun closeKeyboard() {
        binding.root.setOnClickListener {
            val view = requireActivity().currentFocus
            if (view != null) {
                val inputMethodManager: InputMethodManager =
                    requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
            }
        }

        binding.etNickname.setOnEditorActionListener { _, action, _ ->
            var handled = false
            if (action == EditorInfo.IME_ACTION_DONE) {
                val inputMethodManager: InputMethodManager =
                    requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(view?.windowToken, 0)
                handled = true
            }
            handled
        }
    }
}