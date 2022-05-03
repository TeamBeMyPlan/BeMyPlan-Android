package co.kr.bemyplan.ui.login.signup

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import co.kr.bemyplan.R
import co.kr.bemyplan.databinding.FragmentCheckNicknameBinding
import co.kr.bemyplan.ui.login.viewmodel.LoginViewModel
import co.kr.bemyplan.util.CustomDialog
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class CheckNicknameFragment : Fragment() {
    private var _binding: FragmentCheckNicknameBinding? = null
    private val binding get() = _binding ?: error("binding not initialized")
    private val viewModel by activityViewModels<LoginViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.fragment_check_nickname,
            container,
            false
        )
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
            Timber.d(viewModel.nickname.value.toString())
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
                    parentFragmentManager.commit {
                        remove(this@CheckNicknameFragment)
                    }
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
                        parentFragmentManager.commit {
                            replace<CheckEmailFragment>(R.id.fcv_sign_up, CHECK_EMAIL_FRAGMENT)
                            addToBackStack(CHECK_EMAIL_FRAGMENT)
                        }
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

    companion object {
        const val CHECK_EMAIL_FRAGMENT = "CheckEmailFragment"
    }
}