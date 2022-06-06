package co.kr.bemyplan.ui.login.viewmodel

import android.os.Bundle
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.kr.bemyplan.data.entity.login.signup.RequestSignUp
import co.kr.bemyplan.data.local.BeMyPlanDataStore
import co.kr.bemyplan.data.local.FirebaseDefaultEventParameters
import co.kr.bemyplan.domain.model.login.UserInfoModel
import co.kr.bemyplan.domain.repository.GoogleLoginRepository
import co.kr.bemyplan.domain.repository.LoginRepository
import co.kr.bemyplan.util.SingleLiveEvent
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.regex.Pattern
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginRepository: LoginRepository,
    private val googleLoginRepository: GoogleLoginRepository
) : ViewModel() {
    @Inject
    lateinit var dataStore: BeMyPlanDataStore

    val fb = Firebase.analytics.apply {
        setDefaultEventParameters(FirebaseDefaultEventParameters.parameters)
    }

    var nickname = MutableLiveData<String>("")
    var email = MutableLiveData<String>("")

    private var _socialToken = MutableLiveData<String>()
    val socialToken: LiveData<String> get() = _socialToken

    private var _socialType = MutableLiveData<String>()
    val socialType: LiveData<String> get() = _socialType

    private var _userInfo = MutableLiveData<UserInfoModel>()
    val userInfo: LiveData<UserInfoModel> get() = _userInfo

    private var _isUser = SingleLiveEvent<Boolean>()
    val isUser: LiveData<Boolean> get() = _isUser

    private var _isAllAgree = MutableLiveData<Boolean>(false)
    val isAllAgree: LiveData<Boolean> get() = _isAllAgree

    private var _isTermsAgree = MutableLiveData<Boolean>(false)
    val isTermsAgree: LiveData<Boolean> get() = _isTermsAgree

    private var _isInfoAgree = MutableLiveData<Boolean>(false)
    val isInfoAgree: LiveData<Boolean> get() = _isInfoAgree

    // 닉네임 중복여부
    private var _isDuplicatedNickname = MutableLiveData<Boolean?>(null)
    val isDuplicatedNickname: LiveData<Boolean?> get() = _isDuplicatedNickname

    // 닉네임 문법적 유효여부
    private var _isValidNickname = MutableLiveData<Boolean>(false)
    val isValidNickname: LiveData<Boolean> get() = _isValidNickname

    // 이메일 문법적 유효여부
    private var _isValidEmail = MutableLiveData<Boolean>(true)
    val isValidEmail: LiveData<Boolean> get() = _isValidEmail

    private val _nicknamePermission = SingleLiveEvent<Boolean>()
    val nicknamePermission: LiveData<Boolean> get() = _nicknamePermission

    private val _emailPermission = SingleLiveEvent<Boolean>()
    val emailPermission: LiveData<Boolean> get() = _emailPermission

    private val _signUpPermission = SingleLiveEvent<Boolean>()
    val signUpPermission: LiveData<Boolean> get() = _signUpPermission

    private var _isMember = MutableLiveData<Boolean>()
    val isMember: LiveData<Boolean> get() = _isMember

    fun setSocialToken(token: String) {
        _socialToken.value = token
    }

    fun setSocialType(type: String) {
        _socialType.value = type
    }

    fun getAccessToken(
        grantType: String, clientId: String, clientSecret: String, redirectUri: String, code: String
    ) {
        viewModelScope.launch {
            kotlin.runCatching {
                googleLoginRepository.postGoogleLogin(
                    grantType,
                    clientId,
                    clientSecret,
                    redirectUri,
                    code
                )
            }.onSuccess { token ->
                _socialToken.value = token
            }.onFailure { error ->
                Timber.e(error)
            }
        }
    }

    fun login() {
        viewModelScope.launch {
            kotlin.runCatching {
                loginRepository.postLogin(
                    requireNotNull(socialType.value),
                    requireNotNull(socialToken.value)
                )
            }.onSuccess {
                // FB LOG
                fb.logEvent("signin", Bundle().apply {
                    putString("source", socialType.value)
                })
                with(dataStore) {
                    sessionId = it.sessionId
                    userId = it.userId
                    nickname = it.nickname
                }
                _userInfo.value = it
                _isUser.value = true
            }.onFailure { exception ->
                when (exception) {
                    is retrofit2.HttpException -> {
                        if (exception.code() == 404) {
                            _isUser.value = false
                        }
                    }
                    else -> {
                        Timber.e(exception)
                    }
                }
            }
        }
    }

    fun setAllAgree() {
        Timber.tag("mlog: setAllAgree()").d("executed")
        _isAllAgree.value = !requireNotNull(_isAllAgree.value)
        Timber.tag("mlog: isAllAgree.value").d(isAllAgree.value.toString())
        when (requireNotNull(_isAllAgree.value)) {
            true -> {
                _isTermsAgree.value = true
                _isInfoAgree.value = true
            }
            false -> {
                _isTermsAgree.value = false
                _isInfoAgree.value = false
            }
        }
        Timber.tag("mlog: isTermsAgree.value").d(isTermsAgree.value.toString())
        Timber.tag("mlog: isInfoAgree.value").d(isInfoAgree.value.toString())
    }

    fun setTermsAgree() {
        _isTermsAgree.value = !requireNotNull(_isTermsAgree.value)
        _isAllAgree.value = requireNotNull(isTermsAgree.value) && requireNotNull(isInfoAgree.value)
    }

    fun setInfoAgree() {
        _isInfoAgree.value = !requireNotNull(_isInfoAgree.value)
        _isAllAgree.value = requireNotNull(isTermsAgree.value) && requireNotNull(isInfoAgree.value)
    }

    fun setIsDuplicatedNicknameNull() {
        _isDuplicatedNickname.value = null
    }

    private fun checkIsDuplicatedNickname() {
        viewModelScope.launch {
            kotlin.runCatching {
                loginRepository.postDuplicatedNickname(nickname.value.toString())
            }.onSuccess {
                _isDuplicatedNickname.value = false
                if (isDuplicatedNickname.value == false && isValidNickname.value == true) {
                    _nicknamePermission.value = true
                }
            }.onFailure { exception ->
                when (exception) {
                    is retrofit2.HttpException -> {
                        if (exception.code() == 409) {
                            _isDuplicatedNickname.value = true
                        }
                    }
                }
            }
        }
    }

    fun checkIsValidNickname() {
        val regex = "[가-힣A-Za-z0-9]{0,20}".toRegex()
        _isValidNickname.value = nickname.value?.matches(regex)
        Timber.tag("mlog: checkIsValidNickname()").d(isValidNickname.value.toString())
    }

    fun checkIsValidEmail() {
        val pattern: Pattern = Patterns.EMAIL_ADDRESS
        _isValidEmail.value = pattern.matcher(email.value.toString()).matches()
        Timber.tag("mlog: checkIsValidEmail").d(isValidEmail.value.toString())
    }

    fun clickNicknameNext() {
        checkIsDuplicatedNickname()
    }

    fun clickEmailNext() {
        if (isValidEmail.value!!) {
            _emailPermission.value = true
        }
    }

    fun clickTermsNext() {
        if (nicknamePermission.value == true && emailPermission.value == true && isAllAgree.value == true) {
            _signUpPermission.value = true
        }
    }

    fun signUp() {
        viewModelScope.launch {
            kotlin.runCatching {
                loginRepository.postSignUp(
                    RequestSignUp(
                        socialToken.value.toString(),
                        socialType.value.toString(),
                        nickname.value.toString(),
                        email.value.toString()
                    )
                )
            }.onSuccess {
                // FB LOG
                fb.logEvent("signUpComplete", Bundle().apply {
                    putString("source", socialType.value)
                })
                _userInfo.value = it.data
            }.onFailure {
                Timber.e(it.message.toString())
            }
        }
    }
}