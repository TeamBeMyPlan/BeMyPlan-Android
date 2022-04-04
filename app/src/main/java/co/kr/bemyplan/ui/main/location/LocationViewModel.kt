package co.kr.bemyplan.ui.main.location

import android.util.Log
import android.view.LayoutInflater
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import co.kr.bemyplan.data.api.ApiService
import co.kr.bemyplan.data.entity.main.location.ResponseLocationData
import co.kr.bemyplan.databinding.ActivityMainBinding
import co.kr.bemyplan.databinding.ItemLocationBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.properties.Delegates

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
                        if(_location.value!=data.data)
                            _location.value=data.data
                        Log.d("yongminServer", "여행지뷰 서버통신성공!")
                    } else{Log.d("yongminServer", "서버통신실패1")}
                }else{Log.d("yongminServer", "서버통신실패2")}
            }

            override fun onFailure(call: Call<ResponseLocationData>, t: Throwable) {
                Log.d("yongminServer", "여행지서버통신실패3")
                Log.d("yongminServer", "${t.message}")
            }
        })
    }
}