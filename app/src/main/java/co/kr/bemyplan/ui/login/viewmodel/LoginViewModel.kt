package co.kr.bemyplan.ui.login.viewmodel

import android.util.Log
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.kr.bemyplan.data.entity.login.UserInfoModel
import co.kr.bemyplan.data.entity.login.check.RequestDuplicatedNickname
import co.kr.bemyplan.data.entity.login.login.RequestLogin
import co.kr.bemyplan.data.entity.login.signup.RequestSignUp
import co.kr.bemyplan.data.repository.login.LoginRepository
import co.kr.bemyplan.data.repository.login.LoginRepositoryImpl
import co.kr.bemyplan.util.SingleLiveEvent
import com.kakao.sdk.user.UserApiClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.IOException
import java.util.regex.Pattern
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginRepository: LoginRepository
) : ViewModel() {
    // 카카오로그인
    private val userApiClient = UserApiClient.instance

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

    // 이메일 중복여부
    private var _isDuplicatedEmail = MutableLiveData<Boolean?>(null)
    val isDuplicatedEmail get() = _isDuplicatedEmail

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

    fun login() {
        val requestLogin = RequestLogin(socialToken.value.toString(), socialType.value.toString())
        viewModelScope.launch {
            try {
                val response = loginRepository.postLogin(requestLogin)
                _userInfo.value = response.data
                _isUser.value = true
            } catch (e: retrofit2.HttpException) {
                e.printStackTrace()
                Log.e("mlog: HttpException", e.code().toString())
                if (e.code() == 403) {
                    _isUser.value = false
                }
            } catch (e: IOException) {
                e.printStackTrace()
                Log.e("mlog: IOException", e.message.toString())
            } catch (t: Throwable) {
                t.printStackTrace()
                Log.e("mlog: Throwable", t.message.toString())
            }
        }
    }

    fun setAllAgree() {
        Log.d("mlog: setAllAgree()", "executed")
        _isAllAgree.value = !_isAllAgree.value!!
        Log.d("mlog: isAllAgree.value", isAllAgree.value.toString())
        when (_isAllAgree.value!!) {
            true -> {
                _isTermsAgree.value = true
                _isInfoAgree.value = true
            }
            false -> {
                _isTermsAgree.value = false
                _isInfoAgree.value = false
            }
        }
        Log.d("mlog: isTermsAgree.value", isTermsAgree.value.toString())
        Log.d("mlog: isInfoAgree.value", isInfoAgree.value.toString())
    }

    fun setTermsAgree() {
        _isTermsAgree.value = !_isTermsAgree.value!!
        _isAllAgree.value = isTermsAgree.value!! && isInfoAgree.value!!
    }

    fun setInfoAgree() {
        _isInfoAgree.value = !_isInfoAgree.value!!
        _isAllAgree.value = isTermsAgree.value!! && isInfoAgree.value!!
    }

    fun setIsDuplicatedNicknameNull() {
        _isDuplicatedNickname.value = null
    }

    fun setIsDuplicatedEmailNull() {
        _isDuplicatedEmail.value = null
    }

    fun checkIsDuplicatedNickname() {
        viewModelScope.launch {
            kotlin.runCatching {
                loginRepository.postDuplicatedNickname(RequestDuplicatedNickname(nickname.value.toString()))
            }.onSuccess {
                _isDuplicatedNickname.value = it.data.duplicated

                if (!isDuplicatedNickname.value!! && isValidNickname.value!!) {
                    _nicknamePermission.value = true
                }
            }.onFailure {
                Log.e("mlog: checkIsDuplicatedNickname", it.message.toString())
            }
        }
    }

    fun checkIsValidNickname() {
        val regex = "[가-힣A-Za-z0-9]{0,20}".toRegex()
        _isValidNickname.value = nickname.value?.matches(regex)
        Log.d("mlog: isValidNickname.value", isValidNickname.value.toString())
    }

    fun checkIsDuplicatedEmail() {
        // test - bemyplan@gmail.com 쓰면 중복이라고 처리함
        if (email.value == "bemyplan@gmail.com") {
            _isDuplicatedEmail.value = true
        } else {
            _isDuplicatedEmail.value = false
        }

        if (!isDuplicatedEmail.value!! && isValidEmail.value!!) {
            _emailPermission.value = true
        }
    }

    fun checkIsValidEmail() {
        val pattern: Pattern = Patterns.EMAIL_ADDRESS
        _isValidEmail.value = pattern.matcher(email.value.toString()).matches()
        Log.d("mlog: isValidEmail.value", isValidEmail.value.toString())
    }

    fun clickNicknameNext() {
        checkIsDuplicatedNickname()
    }

    fun clickEmailNext() {
        checkIsDuplicatedEmail()
    }

    fun clickTermsNext() {
        Log.d("mlog: nickname", nicknamePermission.value.toString())
        Log.d("mlog: email", emailPermission.value.toString())
        Log.d("mlog: terms", isAllAgree.value.toString())
        if (nicknamePermission.value == true && emailPermission.value == true && isAllAgree.value == true) {
            _signUpPermission.value = true
        }
    }

    fun signUp() {
        viewModelScope.launch {
            try {
                val response = loginRepository.postSignUp(
                    RequestSignUp(
                        socialToken.value.toString(),
                        socialType.value.toString(),
                        nickname.value.toString()
                    )
                )
                _userInfo.value = response.data
            } catch (e: retrofit2.HttpException) {
                Log.e("mlog: LoginViewModel::signUp()", e.message().toString())
            } catch (t: Throwable) {
                Log.e("mlog: LoginViewModel::signUp", t.message.toString())
            }
        }
    }

    companion object {
        const val KAKAO = "KAKAO"
        const val GOOGLE = "GOOGLE"
    }
}