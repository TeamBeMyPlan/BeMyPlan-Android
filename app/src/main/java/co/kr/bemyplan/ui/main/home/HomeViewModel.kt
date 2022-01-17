package co.kr.bemyplan.ui.main.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import androidx.lifecycle.ViewModel
import co.kr.bemyplan.data.api.ApiService
import co.kr.bemyplan.data.entity.main.home.ResponseHomePopularData
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel: ViewModel() {
    private val _popular = MutableLiveData<List<ResponseHomePopularData.Data>>()
    val popular : LiveData<List<ResponseHomePopularData.Data>> get() = _popular
//    private lateinit var viewPagerAdapter : HomeViewPagerAdapter


//    private fun initViewPagerAdapter(){
//        viewPagerAdapter = HomeViewPagerAdapter()
//
//    }

    private fun initNetwork(){
        val call : Call<ResponseHomePopularData> = ApiService.homePopularService.getPopularData()
        call.enqueue(object: Callback<ResponseHomePopularData>{

            override fun onResponse(
                call: Call<ResponseHomePopularData>,
                response: Response<ResponseHomePopularData>
            ) {
                if(response.isSuccessful){
                    val data = response.body()
                    if(data!=null){
                        _popular.value = data.data
                        Log.d("yongminServer", "서버통신성공!")
                    } else{Log.d("yongminServer", "서버통신실패1")}
                }
                else{Log.d("yongminServer", "서버통신실패2") }
            }

            override fun onFailure(call: Call<ResponseHomePopularData>, t: Throwable) {
                Log.d("yongminServer", "서버통신실패3")
            }


        })
    }
}

