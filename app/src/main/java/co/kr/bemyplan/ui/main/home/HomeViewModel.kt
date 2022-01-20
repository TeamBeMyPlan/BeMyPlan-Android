package co.kr.bemyplan.ui.main.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import androidx.lifecycle.ViewModel
import co.kr.bemyplan.data.api.ApiService
import co.kr.bemyplan.data.entity.main.home.ResponseHomeData
import co.kr.bemyplan.data.entity.main.home.ResponseHomePopularData
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel: ViewModel() {

    private val _popular = MutableLiveData<List<ResponseHomePopularData.Data>>()
    val popular : LiveData<List<ResponseHomePopularData.Data>> get() = _popular
    private val _new = MutableLiveData<List<ResponseHomeData.ResponseHomeItems.HomeData>>()
    val new : LiveData<List<ResponseHomeData.ResponseHomeItems.HomeData>> get() = _new
    private val _suggest = MutableLiveData<List<ResponseHomeData.ResponseHomeItems.HomeData>>()
    val suggest : LiveData<List<ResponseHomeData.ResponseHomeItems.HomeData>> get() = _suggest

    fun initSuggestNetwork(){
        val call : Call<ResponseHomeData> = ApiService.homeSuggestService.getSuggestData()
        call.enqueue(object:Callback<ResponseHomeData>{
            override fun onResponse(
                call: Call<ResponseHomeData>,
                response: Response<ResponseHomeData>
            ) {
                if(response.isSuccessful){
                    val data = response.body()
                    if(data!=null){
                        _suggest.value=data.data.items
                        Log.d("yongminSuggestServer", "최신일정서버통신성공!")
                    }else{Log.d("yongminSuggestServer", "최신일정서버통신실패1")}
                }else{Log.d("yongminSuggestServer", "최신일정서버통신실패2")}
            }

            override fun onFailure(call: Call<ResponseHomeData>, t: Throwable) {
                Log.d("yongminSuggestServer", "최신일정서버통신실패3")
            }
        })
    }

    fun initNewNetwork(){
        val call : Call<ResponseHomeData> = ApiService.homeNewService.getNewData()
        call.enqueue(object:Callback<ResponseHomeData>{
            override fun onResponse(
                call: Call<ResponseHomeData>,
                response: Response<ResponseHomeData>
            ) {
                if(response.isSuccessful){
                    val data = response.body()
                    if(data!=null){
                        _new.value=data.data.items
                        Log.d("yongminNewServer", "최신일정서버통신성공!")
                    }else{Log.d("yongminNewServer", "최신일정서버통신실패1")}
                }else{Log.d("yongminNewServer", "최신일정서버통신실패2")}
            }

            override fun onFailure(call: Call<ResponseHomeData>, t: Throwable) {
                Log.d("yongminNewServer", t.message.toString())
            }
        })
    }

    fun initPopularNetwork(){
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

