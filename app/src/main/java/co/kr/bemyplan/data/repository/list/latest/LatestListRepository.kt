package co.kr.bemyplan.data.repository.list.latest

import co.kr.bemyplan.data.entity.list.ResponseLatestList

interface LatestListRepository {
    suspend fun getNewList(page: Int, pageSize: Int): ResponseLatestList
}