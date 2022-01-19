package co.kr.bemyplan.data.repository.list.new

import co.kr.bemyplan.data.entity.list.ResponseNewList

interface NewListRepository {
    suspend fun getNewList(page: Int, pageSize: Int): ResponseNewList
}