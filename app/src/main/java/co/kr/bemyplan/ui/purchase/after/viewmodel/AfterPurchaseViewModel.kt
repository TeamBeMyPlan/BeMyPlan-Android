package co.kr.bemyplan.ui.purchase.after.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.kr.bemyplan.data.repository.purchase.after.AfterPurchaseRepositoryImpl
import co.kr.bemyplan.ui.purchase.after.example.ExampleDummy
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AfterPurchaseViewModel @Inject constructor(
    private val afterPurchaseRepositoryImpl: AfterPurchaseRepositoryImpl
): ViewModel() {
    // post 들고오기
    private var _post = MutableLiveData<PlanDetailResponse>()
    val post: LiveData<PlanDetailResponse>
        get() = _post

    // 일차별 spot
    private var _dailySpots = MutableLiveData<List<SpotDetailResponse>>()
    val dailySpots: LiveData<List<SpotDetailResponse>>
        get() = _dailySpots

    // 서버 통신
    fun getPost(postId: Int) {
        viewModelScope.launch {
            try {
                val response = afterPurchaseRepositoryImpl.getAfterPost(postId)
                _post.value = response.data!!
            } catch (e: retrofit2.HttpException) {
                Log.e(
                    "mlog: AfterPurchaseViewModel::getPost error handling",
                    e.code().toString()
                )
            } catch (t: Throwable) {
                Log.e(
                    "mlog: AfterPurchaseViewModel::getPost error handling",
                    t.message.toString()
                )
            }
        }
    }

    // 더미데이터 생성
    fun initDummy() {
        val dummy = ExampleDummy().getPost()
        _post.value = dummy
    }

    init {
        Log.i("ViewModel", "ViewModel created!")
    }

    // 일차별 장소들 초기화
    fun setDailySpot(dailySpots: List<SpotDetailResponse>) {
        _dailySpots.value = dailySpots
    }
}