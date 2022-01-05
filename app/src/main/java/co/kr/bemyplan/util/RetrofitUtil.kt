package co.kr.bemyplan.util

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

fun <ResponseType> Call<ResponseType>.enqueueUtil(
    onSuccess: (ResponseType) -> Unit,
    onError: ((stateCode: Int) -> Unit)? = null
) {
    this.enqueue(object : Callback<ResponseType> {
        override fun onResponse(call: Call<ResponseType>, response: Response<ResponseType>) {
            if (response.isSuccessful) {
                onSuccess.invoke(response.body() ?: return)
            } else {
                onError?.invoke(response.code())
                Log.d("Network", "error")
                Log.d("Network", "$response.errorBody()")
                Log.d("Network", response.message())
                Log.d("Network", "${response.code()}")
            }
        }

        override fun onFailure(call: Call<ResponseType>, t: Throwable) {
            Log.d("Network", "failed:$t")
        }
    })
}