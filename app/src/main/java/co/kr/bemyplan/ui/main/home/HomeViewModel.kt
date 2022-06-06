package co.kr.bemyplan.ui.main.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.kr.bemyplan.domain.model.main.home.HomeDomainData
import co.kr.bemyplan.domain.repository.HomeNewRepository
import co.kr.bemyplan.domain.repository.HomePopularRepository
import co.kr.bemyplan.domain.repository.HomeSuggestRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homePopularRepository: HomePopularRepository,
    private val homeNewRepository: HomeNewRepository,
    private val homeSuggestRepository: HomeSuggestRepository
) : ViewModel() {

    private val _popular = MutableLiveData<List<HomeDomainData>>()
    val popular : LiveData<List<HomeDomainData>> get() = _popular
    private val _new = MutableLiveData<List<HomeDomainData>>()
    val new : LiveData<List<HomeDomainData>> get() = _new
    private val _suggest = MutableLiveData<List<HomeDomainData>>()
    val suggest : LiveData<List<HomeDomainData>> get() = _suggest

    fun getPopularData(){
        viewModelScope.launch {
            kotlin.runCatching {
                homePopularRepository.getHomePopularData()
            }.onSuccess { responsePopularData->
                if(_popular.value != responsePopularData)
                    _popular.value = responsePopularData
            }.onFailure { error ->
                Log.d("ServerPopular", error.toString())
            }
        }
    }

    fun getNewData(){
        viewModelScope.launch {
            kotlin.runCatching {
                homeNewRepository.getHomeNewData()
            }.onSuccess { responseNewData->
                if(_new.value != responseNewData)
                    _new.value = responseNewData
            }.onFailure { error ->
                Log.d("ServerNew", error.toString())
            }
        }
    }

    fun getSuggestData(){
        viewModelScope.launch {
            kotlin.runCatching {
                homeSuggestRepository.getHomeSuggestData()
            }.onSuccess { responseSuggestData->
                if(_suggest.value != responseSuggestData)
                    _suggest.value = responseSuggestData
            }.onFailure { error ->
                Log.d("ServerSuggest", error.toString())
            }
        }
    }



    /*fun initSuggestNetwork(){
        val call : Call<ResponseHomeData> = ApiService.homeSuggestService.getSuggestData()
        call.enqueue(object:Callback<ResponseHomeData>{
            override fun onResponse(
                call: Call<ResponseHomeData>,
                response: Response<ResponseHomeData>
            ) {
                if(response.isSuccessful){
                    val data = response.body()
                    if(data!=null){
                        if(_suggest.value!=data.data.contents)
                            _suggest.value=data.data.contents
                        Log.d("yongminSuggestServer", "추천일정서버통신성공!")
                    }else{Log.d("yongminSuggestServer", "추천일정서버통신실패1")}
                }else{Log.d("yongminSuggestServer", "추천일정서버통신실패2")}
            }

            override fun onFailure(call: Call<ResponseHomeData>, t: Throwable) {
                Log.d("yongminSuggestServer", "추천일정서버통신실패3")
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
                        if(_new.value!=data.data.contents)
                            _new.value=data.data.contents
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
                        if(_popular.value!=data.data)
                            _popular.value = data.data
                        Log.d("yongminServer", "인기일정서버통신성공!")
                    } else{Log.d("yongminServer", "인기일정서버통신실패1")}
                }
                else{Log.d("yongminServer", "인기일정서버통신실패2") }
            }

            override fun onFailure(call: Call<ResponseHomePopularData>, t: Throwable) {
                Log.d("yongminServer", "서버통신실패3")
            }
        })
    }*/
}

