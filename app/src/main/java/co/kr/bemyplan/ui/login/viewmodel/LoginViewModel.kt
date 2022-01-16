package co.kr.bemyplan.ui.login.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import co.kr.bemyplan.util.SingleLiveEvent

class LoginViewModel : ViewModel() {
    var nickname = MutableLiveData<String>("")

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
        if(nickname.value.toString() == "test") {
            // 중복인 경우
            _isDuplicated.value = true
        } else {
            // 중복 아닌 경우
            _isDuplicated.value = false
        }
    }

    fun checkIsValid() {
        val regex = "[가-힣A-Za-z0-9]{0,20}".toRegex()
        _isValid.value = nickname.value?.matches(regex)
        Log.d("mlog: isValid.value", isValid.value.toString())
    }

    fun clickSignUp() {
        checkIsDuplicated()

        Log.d("mlog: isDuplicated.value", isDuplicated.value.toString())
        if(!isDuplicated.value!! && isValid.value!!) {
            _signUpPermission.call()
        }
    }
}