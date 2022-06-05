package co.kr.bemyplan.data.entity.main.myplan

import co.kr.bemyplan.domain.model.main.myplan.MyPlanData

data class ResponseMyPlan(
    val data : MyPlanData,
    val message : String,
    val resultCode : String
)
