package co.kr.bemyplan.data.repository.main.scrap

import co.kr.bemyplan.data.entity.main.scrap.ResponseScrap

interface PostScrapRepository {
    suspend fun postScrap(postId: Int): ResponseScrap
}