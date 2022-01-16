package co.kr.bemyplan.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import co.kr.bemyplan.R
import co.kr.bemyplan.databinding.FragmentLoginBinding
import co.kr.bemyplan.ui.login.viewmodel.LoginViewModel
import co.kr.bemyplan.ui.main.MainActivity
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient


class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val viewModel by activityViewModels<LoginViewModel>()
    private val userApiClient = UserApiClient.instance
    private val kakaoLoginCallback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
        if (error != null) {
            Log.d("mlog: kakaoLogin", "로그인 실패")
        } else if (token != null) {
            Log.d("mlog: kakaoLogin", "로그인 성공 ${token.accessToken}")
            viewModel.setKakaoToken(token.accessToken)

            // 회원이면 메인 액티비티로 이동, 회원이 아니면 회원가입 프래그먼트로 이동
            if(viewModel.isMember.value == true) {
                startMainActivity()
            } else if(viewModel.isMember.value == false) {
                startSignUpFragment()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding =
            DataBindingUtil.inflate(layoutInflater, R.layout.fragment_login, container, false)

        clickGuestLogin()
        clickKakaoLogin()
        test()

        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun clickGuestLogin() {
        binding.tvGuestLogin.setOnClickListener {
            val intent = Intent(requireContext(), MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun test() {
        binding.layoutGoogle.setOnClickListener {
            startSignUpFragment()
        }
    }

    private fun clickKakaoLogin() {
        binding.layoutKakao.setOnClickListener {
            getKakaoToken()
        }
    }

    private fun getKakaoToken() {
        if (userApiClient.isKakaoTalkLoginAvailable(requireContext())) {
            Log.d("mlog: kakaoLogin", "카카오톡으로 로그인 가능")
            userApiClient.loginWithKakaoTalk(
                requireContext(),
                callback = kakaoLoginCallback
            )
        } else {
            Log.d("mlog: kakaoLogin", "카카오톡으로 로그인 불가능")
            userApiClient.loginWithKakaoAccount(
                requireContext(),
                callback = kakaoLoginCallback
            )
        }
    }

    private fun startSignUpFragment() {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fcv_login, SignUpFragment())
            .addToBackStack(null)
            .commit()
    }

    private fun startMainActivity() {
        val intent = Intent(requireContext(), MainActivity::class.java)
        startActivity(intent)
        requireActivity().finish()
    }
}