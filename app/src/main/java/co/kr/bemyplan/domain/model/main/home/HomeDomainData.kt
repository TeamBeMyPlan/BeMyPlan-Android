package co.kr.bemyplan.domain.model.main.home

data class HomeDomainData(
    val createdAt : String,
    val orderStatus : Boolean,
    val planId: Int,
    val scrapStatus : Boolean,
    val thumbnailUrl: String,
    val title: String,
    val updatedAt : String,
    val user : UserData,
){
    data class UserData(
        val nickname : String,
        val userId : Int
    )
}
