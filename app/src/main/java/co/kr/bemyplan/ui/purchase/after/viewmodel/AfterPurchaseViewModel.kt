package co.kr.bemyplan.ui.purchase.after.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.kr.bemyplan.data.entity.purchase.after.Post
import co.kr.bemyplan.data.entity.purchase.after.Spot
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
    private var _post = MutableLiveData<Post>()
    val post: LiveData<Post>
        get() = _post

    // 일차별 spot
    private var _dailySpots = MutableLiveData<List<Spot>>()
    val dailySpots: LiveData<List<Spot>>
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
//        val call = ApiService.afterPostService.getPost(postId)
//        call.enqueue(object : Callback<ResponseAfterPost> {
//            override fun onResponse(
//                call: Call<ResponseAfterPost>,
//                response: Response<ResponseAfterPost>
//            ) {
//                if (response.isSuccessful) {
//                    val data = response.body()?.data
//                    data?.let {
//                        _post.value = it
//                    }
//                }
//            }
//
//            override fun onFailure(call: Call<ResponseAfterPost>, t: Throwable) {
//                Log.d("NetworkTest", "error: $t")
//            }
//        })
    }

    // 더미데이터 생성
    fun initDummy() {
        val dummy = ExampleDummy().getPost()
        _post.value = dummy
    }

    init { //TODO 여기서 무엇을 해야할 지 모르겠습니다
        Log.i("ViewModel", "ViewModel created!")
    }

    // 일차별 장소들 초기화
    fun setDailySpot(dailySpots: List<Spot>) {
        _dailySpots.value = dailySpots
    }
}