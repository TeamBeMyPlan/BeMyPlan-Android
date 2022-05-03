package co.kr.bemyplan.ui.login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import co.kr.bemyplan.BuildConfig
import co.kr.bemyplan.R
import co.kr.bemyplan.data.local.BeMyPlanDataStore
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
import timber.log.Timber
import javax.inject.Inject


@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(R.layout.fragment_login) {
    @Inject
    lateinit var dataStore: BeMyPlanDataStore
    private val viewModel by activityViewModels<LoginViewModel>()
    private val userApiClient = UserApiClient.instance

    private val kakaoLoginCallback: (OAuthToken?, Throwable?) -> Unit = { token, _ ->
        if (token != null) {
            Timber.i("카카오계정 로그인 성공 " + token.accessToken)
            viewModel.setSocialToken(token.accessToken)
            viewModel.setSocialType(KAKAO)
            userApiClient.me { user, error ->
                if (error != null) {
                    Timber.e("카카오계정 사용자 정보 가져오기 실패")
                } else if (user != null) {
                    Timber.i("카카오계정 사용자 정보 가져오기 성공, 카카오계정 이메일 = " + user.kakaoAccount?.email)
                    user.kakaoAccount?.email?.let { email -> viewModel.email.value = email }
                }
            }
            login()
        } else {
            Timber.e(token.toString())
            requireContext().shortToast("다시 로그인해주세요")
        }
    }

    private val googleLoginLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { it ->
            if (it.resultCode == Activity.RESULT_OK) {
                kotlin.runCatching {
                    GoogleSignIn.getSignedInAccountFromIntent(it.data)
                }.onSuccess { task ->
                    val account = task.getResult(ApiException::class.java)
                    val authCode = account.serverAuthCode ?: ""
                    viewModel.getAccessToken(
                        "authorization_code",
                        BuildConfig.GOOGLE_WEB_CLIENT_ID,
                        BuildConfig.GOOGLE_CLIENT_SECRET,
                        "",
                        authCode
                    )
                    viewModel.socialToken.observe(viewLifecycleOwner) {
                        viewModel.setSocialType(GOOGLE)
                        account.email?.let { email -> viewModel.email.value = email }
                        login()
                    }
                }.onFailure { error ->
                    Timber.e(error)
                }
            } else {
                if (it.resultCode == Activity.RESULT_CANCELED) {
                    kotlin.runCatching {
                        GoogleSignIn.getSignedInAccountFromIntent(it.data)
                    }.onSuccess { task ->
                        Timber.e(task.exception.toString())
                    }.onFailure { error ->
                        Timber.e(error)
                    }
                }
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
        // TODO: 나중에 꼭 !! 지우세요
        // test용으로 비마이플랜 이미지 클릭 시 회원가입 뷰 보여줌
        binding.ivLogo.setOnClickListener {
            startSignUpFragment()
        }
    }

    private fun getKakaoToken() {
        if (userApiClient.isKakaoTalkLoginAvailable(requireContext())) {
            Timber.i("카카오톡으로 로그인 가능")
            userApiClient.loginWithKakaoTalk(
                requireContext(),
                callback = kakaoLoginCallback
            )
        } else {
            Timber.i("카카오톡으로 로그인 불가능")
            userApiClient.loginWithKakaoAccount(
                requireContext(),
                callback = kakaoLoginCallback
            )
        }
    }

    private fun getGoogleToken() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .requestServerAuthCode(BuildConfig.GOOGLE_WEB_CLIENT_ID)
            .build()
        val googleSignInClient = GoogleSignIn.getClient(requireContext(), gso)
        val signInIntent = googleSignInClient.signInIntent
        googleLoginLauncher.launch(signInIntent)
    }

    private fun login() {
        viewModel.login()

        viewModel.userInfo.observe(viewLifecycleOwner) {
            dataStore.sessionId = it.sessionId
            dataStore.userId = it.userId
            startMainActivity()
        }

        viewModel.isUser.observe(viewLifecycleOwner) {
            when (it) {
                true -> {
                    Timber.i("비마이플랜 로그인 성공")
                }
                else -> {
                    Timber.d("비마이플랜 로그인 실패, 회원가입으로 이동")
                    startSignUpFragment()
                }
            }
        }
    }

    private fun startSignUpFragment() {
        parentFragmentManager.commit {
            replace<SignUpFragment>(R.id.fcv_login, SIGN_UP_FRAGMENT)
            addToBackStack(SIGN_UP_FRAGMENT)
        }
    }

    private fun startMainActivity() {
        val intent = Intent(requireContext(), MainActivity::class.java)
        startActivity(intent)
        requireActivity().finish()
    }

    companion object {
        const val KAKAO = "KAKAO"
        const val GOOGLE = "GOOGLE"
        const val SIGN_UP_FRAGMENT = "SignUpFragment"
    }
}