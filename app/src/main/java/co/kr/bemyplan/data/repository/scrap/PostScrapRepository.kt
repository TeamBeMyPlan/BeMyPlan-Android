package co.kr.bemyplan.data.repository.scrap

import co.kr.bemyplan.data.entity.scrap.ResponseScrap

interface PostScrapRepository {
    suspend fun postScrap(postId: Int): ResponseScrap
}