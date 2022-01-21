package co.kr.bemyplan.data.entity.main.myplan

import com.google.gson.annotations.SerializedName

data class ResponseWithdraw(
    val status: Int,
    @SerializedName("success")
    val isSuccess: Boolean,
    val message: String
)
