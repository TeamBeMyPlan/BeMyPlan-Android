package co.kr.bemyplan.ui.main.location

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.kr.bemyplan.data.firebase.FirebaseAnalyticsProvider
import co.kr.bemyplan.domain.model.main.location.LocationData
import co.kr.bemyplan.domain.repository.LocationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocationViewModel @Inject constructor(
    private val locationRepository: LocationRepository,
    val firebaseAnalyticsProvider: FirebaseAnalyticsProvider
) : ViewModel() {
    private var _locationData = MutableLiveData<List<LocationData>>()
    val locationData: LiveData<List<LocationData>> get() = _locationData

    fun getLocationData() {
        viewModelScope.launch {
            kotlin.runCatching {
                locationRepository.getLocationData()
            }.onSuccess { responseLocationData ->
                if (_locationData.value != responseLocationData)
                    _locationData.value = responseLocationData
            }.onFailure { error ->
                Log.d("yongminServer", "$error")
            }
        }
    }
}