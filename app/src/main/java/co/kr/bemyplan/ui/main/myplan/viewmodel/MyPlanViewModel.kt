package co.kr.bemyplan.ui.main.myplan.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.kr.bemyplan.data.entity.main.myplan.MyModel
import co.kr.bemyplan.data.repository.main.myplan.MyPlanRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyPlanViewModel @Inject constructor(
    private val myPlanRepository: MyPlanRepository
) : ViewModel() {
    private var page = 0
    private var pageSize = 10

    private var _nickname = MutableLiveData<String>()
    val nickname: LiveData<String> get() = _nickname

    private var _myPlan = MutableLiveData<List<MyModel>>()
    val myPlan: LiveData<List<MyModel>> get() = _myPlan

    fun setNickname(nickname: String) {
        _nickname.value = nickname
    }

    fun getMyPlanList() {
        viewModelScope.launch {
            kotlin.runCatching {
                myPlanRepository.getMyPlan(page, pageSize)
            }.onSuccess {
                if(_myPlan.value != it.data.items) {
                    _myPlan.value = it.data.items
                }
            }.onFailure {
                Log.e("mlog: MyPlanViewModel::getMyPlan error", it.message.toString())
            }
        }
    }
}