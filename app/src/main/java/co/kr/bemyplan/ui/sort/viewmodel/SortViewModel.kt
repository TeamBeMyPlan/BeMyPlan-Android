package co.kr.bemyplan.ui.sort.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import timber.log.Timber

class SortViewModel : ViewModel() {
    private var _sort = MutableLiveData("createdAt,desc")
    val sort: LiveData<String> get() = _sort

    fun setSort(sortType: Int) {
        when (sortType) {
            0 -> _sort.value = "createdAt,desc"
            1 -> _sort.value = "orderCnt,desc"
//            2 -> _sort.value = "createdAt,asc"
            else -> _sort.value = ""
        }
        Timber.tag("mlog: sort").i(sort.value.toString())
    }
}