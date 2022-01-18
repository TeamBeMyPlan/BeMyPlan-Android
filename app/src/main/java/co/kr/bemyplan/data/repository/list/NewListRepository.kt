package co.kr.bemyplan.data.repository.list

import co.kr.bemyplan.data.entity.list.ResponseNewList

interface NewListRepository {
    suspend fun getNewList(page: Int): ResponseNewList
}