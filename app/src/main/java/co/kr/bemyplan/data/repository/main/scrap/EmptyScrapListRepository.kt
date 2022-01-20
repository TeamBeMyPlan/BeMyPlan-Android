package co.kr.bemyplan.data.repository.main.scrap

import co.kr.bemyplan.data.entity.main.scrap.ResponseEmptyScrapList

interface EmptyScrapListRepository {
    suspend fun getEmptyScrapList(): ResponseEmptyScrapList
}