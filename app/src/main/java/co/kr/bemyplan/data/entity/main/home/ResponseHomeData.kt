package co.kr.bemyplan.data.entity.main.home

import co.kr.bemyplan.domain.model.main.home.HomeDomainData

data class ResponseHomeData(
    val data: ResponseHomeItems,
    val message : String,
    val resultCode : String
){
    data class ResponseHomeItems(
        val contents : List<HomeDomainData>,
        val nextCursor : Int
    )
}