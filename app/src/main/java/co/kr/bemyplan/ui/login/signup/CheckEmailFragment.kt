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
import co.kr.bemyplan.databinding.FragmentCheckEmailBinding
import co.kr.bemyplan.ui.login.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CheckEmailFragment : Fragment() {
    private var _binding: FragmentCheckEmailBinding? = null
    private val binding get() = _binding ?: error("binding not initialized")
    private val viewModel by activityViewModels<LoginViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_check_email, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        observeEmail()
        moveNextView()
        clickBack()
        closeKeyboard()
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun observeEmail() {
        viewModel.email.observe(viewLifecycleOwner) {
            Log.d("mlog: email", viewModel.email.value.toString())
            viewModel.checkIsValidEmail()
        }
    }

    private fun moveNextView() {
        viewModel.emailPermission.observe(viewLifecycleOwner) {
            val transaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.fcv_sign_up, CheckTermsFragment())
                .addToBackStack("terms")
                .commit()
        }
    }

    private fun clickBack() {
        binding.layoutBack.setOnClickListener {
            requireActivity().onBackPressed()
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

        binding.etEmail.setOnEditorActionListener { _, action, _ ->
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