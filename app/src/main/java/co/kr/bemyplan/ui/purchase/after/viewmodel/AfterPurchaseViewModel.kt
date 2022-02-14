package co.kr.bemyplan.ui.purchase.after.viewmodel

import android.util.Log
import androidx.core.view.isVisible
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import co.kr.bemyplan.data.api.ApiService
import co.kr.bemyplan.data.entity.purchase.after.Post
import co.kr.bemyplan.data.entity.purchase.after.ResponseAfterPost
import co.kr.bemyplan.data.entity.purchase.after.Spot
import co.kr.bemyplan.ui.purchase.after.example.ExampleDummy
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AfterPurchaseViewModel: ViewModel() {
    // post 들고오기
    private var _post = MutableLiveData<Post>()
    val post: LiveData<Post>
        get() = _post

    // spot
    private var _spot = MutableLiveData<Spot>()
    val spot: LiveData<Spot>
        get() = spot

    fun initNetwork(postId: Int) {
        val call = ApiService.afterPostService.getPost(postId)
        call.enqueue(object : Callback<ResponseAfterPost> {
            override fun onResponse(
                call: Call<ResponseAfterPost>,
                response: Response<ResponseAfterPost>
            ) {
                if (response.isSuccessful) {
                    val data = response.body()?.data
                    data?.let {
                        _post.value = it
                    }
                }
            }

            override fun onFailure(call: Call<ResponseAfterPost>, t: Throwable) {
                Log.d("NetworkTest", "error: $t")
            }
        })
    }

    // 더미데이터 생성
    fun initDummy() {
        val dummy = ExampleDummy().getPost()
        _post.value = dummy
    }

    init {
        Log.i("ViewModel", "ViewModel created!")
    }

    fun setSpot(index: Int) {
//        _spot.value = post.value.spots[index]
    }

    override fun onCleared() {
        super.onCleared()
    }
}