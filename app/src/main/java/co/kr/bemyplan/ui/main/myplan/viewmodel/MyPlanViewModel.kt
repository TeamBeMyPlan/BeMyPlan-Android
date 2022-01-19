package co.kr.bemyplan.ui.main.myplan.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.kr.bemyplan.data.entity.main.myplan.MyModel
import co.kr.bemyplan.data.repository.main.myplan.MyPlanRepositoryImpl
import kotlinx.coroutines.launch

class MyPlanViewModel: ViewModel() {
    private var page = 0
    private var pageSize = 10

    private var _myPlan = MutableLiveData<List<MyModel>>()
    val myPlan: LiveData<List<MyModel>> get() = _myPlan

    fun getMyPlan(userId: String) {
        val myPlanRepositoryImpl = MyPlanRepositoryImpl()
        viewModelScope.launch {
            val response = myPlanRepositoryImpl.getMyPlan(userId, page, pageSize)
            _myPlan.value = response.data
            Log.d("mlog: MyPlanViewModel.myPlan.size", myPlan.value?.size.toString())
        }
    }
}