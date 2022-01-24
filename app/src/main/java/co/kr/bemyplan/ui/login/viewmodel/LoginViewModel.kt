package co.kr.bemyplan.ui.login.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.kr.bemyplan.data.entity.login.RequestLogin
import co.kr.bemyplan.data.entity.login.UserInfoModel
import co.kr.bemyplan.data.entity.login.check.RequestDuplicatedNickname
import co.kr.bemyplan.data.repository.login.LoginRepositoryImpl
import co.kr.bemyplan.util.SingleLiveEvent
import com.kakao.sdk.user.UserApiClient
import kotlinx.coroutines.launch
import java.io.IOException

class LoginViewModel : ViewModel() {
    // 카카오로그인
    private val userApiClient = UserApiClient.instance
    private val loginRepositoryImpl = LoginRepositoryImpl()

    var nickname = MutableLiveData<String>("")

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

    // 중복여부
    private var _isDuplicated = MutableLiveData<Boolean?>(null)
    val isDuplicated: LiveData<Boolean?> get() = _isDuplicated

    // 문법적으로 유효여부
    private var _isValid = MutableLiveData<Boolean>(false)
    val isValid: LiveData<Boolean> get() = _isValid

    private val _signUpPermission = SingleLiveEvent<Boolean>()
    val signUpPermission: LiveData<Boolean> get() = _signUpPermission

    // 로그인
    private var _loginType = MutableLiveData<String>()
    val loginType: LiveData<String> get() = _loginType

    private var _isMember = MutableLiveData<Boolean>()
    val isMember: LiveData<Boolean> get() = _isMember

    fun setSocialToken(token: String) {
        _socialToken.value = token
        Log.d("mlog: LoginViewModel::socialToken.value", socialToken.value.toString())
    }

    fun setSocialType(type: String) {
        _socialType.value = type
        Log.d("mlog: LoginViewModel::socialType.value", socialType.value.toString())
    }

    fun postLogin() {
        val requestLogin = RequestLogin(socialToken.value.toString(), socialType.value.toString())
        viewModelScope.launch {
            try {
                val response = loginRepositoryImpl.postLogin(requestLogin)
                _userInfo.value = response.data.userInfo
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

    fun setIsDuplicatedNull() {
        _isDuplicated.value = null
    }

    fun checkIsDuplicated() {
        viewModelScope.launch {
            try {
                val response =
                    loginRepositoryImpl.postDuplicatedNickname(RequestDuplicatedNickname(nickname.value.toString()))
                _isDuplicated.value = response.data.duplicated
                Log.d("mlog: LoginViewModel::isDuplicated.value", isDuplicated.value.toString())

                if (!isDuplicated.value!! && isValid.value!!) {
                    _signUpPermission.call()
                }
            } catch (e: retrofit2.HttpException) {
                Log.e("mlog: HttpException", e.code().toString())
            } catch (t: Throwable) {
                Log.e("mlog: Throwable", t.message.toString())
            }
        }
//        if (nickname.value.toString() == "test") {
//            // 중복인 경우
//            _isDuplicated.value = true
//        } else {
//            // 중복 아닌 경우
//            _isDuplicated.value = false
//        }
    }

    fun checkIsValid() {
        val regex = "[가-힣A-Za-z0-9]{0,20}".toRegex()
        _isValid.value = nickname.value?.matches(regex)
        Log.d("mlog: isValid.value", isValid.value.toString())
    }

    fun clickSignUp() {
        checkIsDuplicated()
    }

    companion object {
        const val KAKAO = "KAKAO"
        const val GOOGLE = "GOOGLE"
    }
}