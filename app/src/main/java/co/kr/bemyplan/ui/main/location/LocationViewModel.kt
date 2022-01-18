package co.kr.bemyplan.ui.main.location

import android.content.Intent
import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import co.kr.bemyplan.data.api.ApiService
import co.kr.bemyplan.data.entity.main.location.ResponseLocationData
import co.kr.bemyplan.ui.list.ListActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LocationViewModel:ViewModel() {
    private val _location = MutableLiveData<List<ResponseLocationData.LocationData>>()
    val location : LiveData<List<ResponseLocationData.LocationData>> get() = _location

    fun initLocationNetwork(){
        val call : Call<ResponseLocationData> = ApiService.locationService.getLocationData()
        call.enqueue(object:Callback<ResponseLocationData>{

            override fun onResponse(
                call: Call<ResponseLocationData>,
                response: Response<ResponseLocationData>
            ) {
                if(response.isSuccessful){
                    val data = response.body()
                    if(data!=null){
                        _location.value=data.data
                        Log.d("yongminServer", "서버통신성공!")
                    } else{Log.d("yongminServer", "서버통신실패1")}
                }else{Log.d("yongminServer", "서버통신실패2")}
            }

            override fun onFailure(call: Call<ResponseLocationData>, t: Throwable) {
                Log.d("yongminServer", "서버통신실패3")
            }

        })
    }
}