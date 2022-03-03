package co.kr.bemyplan.ui.sort.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SortViewModel : ViewModel() {
    private var _sort = MutableLiveData("created_at")
    val sort: LiveData<String> get() = _sort

    fun setSort(sortType: Int) {
        when (sortType) {
            0 -> _sort.value = "created_at"
            1 -> _sort.value = "order_count"
            2 -> _sort.value = "price"
            else -> _sort.value = ""
        }
        Log.d("mlog: sort", sort.value.toString())
    }
}