package co.kr.bemyplan.ui.login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import co.kr.bemyplan.BuildConfig
import co.kr.bemyplan.R
import co.kr.bemyplan.data.local.AutoLoginData
import co.kr.bemyplan.databinding.FragmentLoginBinding
import co.kr.bemyplan.ui.base.BaseFragment
import co.kr.bemyplan.ui.login.viewmodel.LoginViewModel
import co.kr.bemyplan.ui.main.MainActivity
import co.kr.bemyplan.util.ToastMessage.shortToast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(R.layout.fragment_login) {
    private val viewModel by activityViewModels<LoginViewModel>()
    private val userApiClient = UserApiClient.instance

    private val kakaoLoginCallback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
        if (token != null) {
            Log.d("mlog: kakaoLogin", "카카오계정 로그인 성공 ${token.accessToken}")
            viewModel.setSocialToken(token.accessToken)
            viewModel.setSocialType("KAKAO")

            // 이메일 가져오기
            userApiClient.me { user, error ->
                if (error != null) {
                    Log.d("mlog: kakaoLogin", "카카오계정 사용자 정보 가져오기 실패")
                } else if (user != null) {
                    Log.d(
                        "mlog: kakaoLogin",
                        "카카오계정 사용자 정보 가져오기 성공, 카카오계정 이메일 = ${user.kakaoAccount?.email}"
                    )
                    user.kakaoAccount?.email?.let { email -> viewModel.email.value = email }
                }
            }

            Log.d("mlog: LoginFragment::socialToken", viewModel.socialToken.value.toString())
            Log.d("mlog: LoginFragment::socialType", viewModel.socialType.value.toString())
            login()
        } else {
            requireContext().shortToast("다시 로그인해주세요")
        }
    }

    private val startForGoogleLoginResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(it.data)
                try {
                    val account = task.getResult(ApiException::class.java)
                    Log.d(
                        "mlog: googleLogin",
                        "구글 로그인 성공, account.id = " + account.id + ", account.idToken = " + account.idToken + ", account.email = " + account.email
                    )
                    viewModel.setSocialToken(account.idToken)
                    viewModel.setSocialType("GOOGLE")
                    account.email?.let { email -> viewModel.email.value = email }
                    Log.d(
                        "mlog: LoginFragment::socialToken",
                        viewModel.socialToken.value.toString()
                    )
                    Log.d("mlog: LoginFragment::socialType", viewModel.socialType.value.toString())
                    login()
                } catch (e: ApiException) {
                    Log.w("mlog: googleLogin", "구글 로그인 실패: " + e.message)
                }
            } else {
                requireContext().shortToast("다시 로그인해주세요")
            }
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        clickGuestLogin()
        clickKakaoLogin()
        clickGoogleLogin()
        testForSignUpPage()
    }

    private fun clickGuestLogin() {
        binding.tvGuestLogin.setOnClickListener {
            // Firebase Event Log
            val bundle = Bundle()
            bundle.putString("source", "Guest")
            viewModel.fb.logEvent("signin", bundle)

            startMainActivity()
        }
    }

    private fun clickKakaoLogin() {
        binding.layoutKakao.setOnClickListener {
            getKakaoToken()
        }
    }

    private fun clickGoogleLogin() {
        binding.layoutGoogle.setOnClickListener {
            getGoogleToken()
        }
    }

    private fun testForSignUpPage() {
        // test용으로 비마이플랜 이미지 클릭 시 회원가입 뷰 보여줌
        binding.ivLogo.setOnClickListener {
            startSignUpFragment()
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

    private fun getGoogleToken() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .requestIdToken(BuildConfig.GOOGLE_CLIENT_ID)
            .build()
        val googleSignInClient = GoogleSignIn.getClient(requireContext(), gso)

        val signInIntent = googleSignInClient.signInIntent
        startForGoogleLoginResult.launch(signInIntent)
    }

    private fun login() {
        viewModel.login()

        viewModel.userInfo.observe(viewLifecycleOwner) {
            AutoLoginData.setAutoLogin(requireContext(), true, it.sessionId, it.userId)
            startMainActivity()
        }

        viewModel.isUser.observe(viewLifecycleOwner) {
            when (it) {
                true -> {
                    Log.d("mlog: LoginFragment", "isUser == true, go main")
                }
                else -> {
                    Log.d("mlog: LoginFragment", "status code == 403 && isUser == false")
                    startSignUpFragment()
                }
            }
        }
    }

    private fun startSignUpFragment() {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fcv_login, SignUpFragment(), "signUpFragment")
            .addToBackStack(null)
            .commit()
    }

    private fun startMainActivity() {
        val intent = Intent(requireContext(), MainActivity::class.java)
        startActivity(intent)
        requireActivity().finish()
    }
}