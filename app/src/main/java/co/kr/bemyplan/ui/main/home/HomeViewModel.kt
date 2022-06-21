package co.kr.bemyplan.ui.main.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.kr.bemyplan.domain.model.main.home.HomeDomainData
import co.kr.bemyplan.domain.repository.CheckPurchasedRepository
import co.kr.bemyplan.domain.repository.HomeNewRepository
import co.kr.bemyplan.domain.repository.HomePopularRepository
import co.kr.bemyplan.domain.repository.HomeSuggestRepository
import co.kr.bemyplan.util.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homePopularRepository: HomePopularRepository,
    private val homeNewRepository: HomeNewRepository,
    private val homeSuggestRepository: HomeSuggestRepository,
    private val homeCheckPurchasedRepository: CheckPurchasedRepository
) : ViewModel() {
    private val _popular = MutableLiveData<List<HomeDomainData>>()
    val popular: LiveData<List<HomeDomainData>> get() = _popular
    private val _new = MutableLiveData<List<HomeDomainData>>()
    val new: LiveData<List<HomeDomainData>> get() = _new
    private val _suggest = MutableLiveData<List<HomeDomainData>>()
    val suggest: LiveData<List<HomeDomainData>> get() = _suggest

    val isPurchased = SingleLiveEvent<Unit>()
    val isNotPurchased = SingleLiveEvent<Unit>()

    fun getPopularData() {
        viewModelScope.launch {
            kotlin.runCatching {
                homePopularRepository.getHomePopularData(size = 10, sort = "orderCnt,desc")
            }.onSuccess { responsePopularData ->
                if (_popular.value != responsePopularData)
                    _popular.value = responsePopularData
            }.onFailure { error ->
                Timber.e(error)
            }
        }
    }

    fun getNewData() {
        viewModelScope.launch {
            kotlin.runCatching {
                homeNewRepository.getHomeNewData(size = 5, sort = "createdAt,desc")
            }.onSuccess { responseNewData ->
                if (_new.value != responseNewData)
                    _new.value = responseNewData
            }.onFailure { error ->
                Timber.e(error)
            }
        }
    }

    fun getSuggestData() {
        viewModelScope.launch {
            kotlin.runCatching {
                homeSuggestRepository.getHomeSuggestData(size = 5, sort = "id,desc")
            }.onSuccess { responseSuggestData ->
                if (_suggest.value != responseSuggestData)
                    _suggest.value = responseSuggestData
            }.onFailure { error ->
                Timber.e(error)
            }
        }
    }

    fun checkPurchased(planId: Int) {
        viewModelScope.launch {
            runCatching {
                homeCheckPurchasedRepository.checkPurchased(planId)
            }.onSuccess {
                isNotPurchased.call()
            }.onFailure {
                isPurchased.call()
            }
        }
    }
}