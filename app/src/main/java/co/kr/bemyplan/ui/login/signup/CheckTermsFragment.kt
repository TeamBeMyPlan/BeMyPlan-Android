package co.kr.bemyplan.ui.login.signup

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import co.kr.bemyplan.R
import co.kr.bemyplan.data.local.BeMyPlanDataStore
import co.kr.bemyplan.databinding.FragmentCheckTermsBinding
import co.kr.bemyplan.ui.login.viewmodel.LoginViewModel
import co.kr.bemyplan.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CheckTermsFragment : Fragment() {
    private var _binding: FragmentCheckTermsBinding? = null
    private val binding get() = _binding ?: error("binding not initialized")
    private val viewModel by activityViewModels<LoginViewModel>()
    @Inject
    private lateinit var dataStore: BeMyPlanDataStore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_check_terms, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        clickTermsDetail()
        clickInfoDetail()
        signUp()
        observeLiveData()
        clickBack()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun clickTermsDetail() {
        binding.layoutAgreeTermsDetail.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.term_url)))
            startActivity(intent)
        }
    }

    private fun clickInfoDetail() {
        binding.layoutAgreeInfoDetail.setOnClickListener {

        }
    }

    private fun signUp() {
        viewModel.signUpPermission.observe(viewLifecycleOwner) {
            viewModel.signUp()
        }
    }

    private fun observeLiveData() {
        viewModel.userInfo.observe(viewLifecycleOwner) { userInfo ->
            val intent = Intent(requireContext(), MainActivity::class.java)
            dataStore.sessionId = userInfo.sessionId
            dataStore.userId = userInfo.userId
            startActivity(intent)
            requireActivity().finish()
        }
    }

    private fun clickBack() {
        binding.layoutBack.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }
}