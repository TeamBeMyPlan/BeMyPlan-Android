package co.kr.bemyplan.domain.repository

import co.kr.bemyplan.data.entity.purchase.before.ResponsePreviewInfo
import co.kr.bemyplan.data.entity.purchase.before.ResponsePreviewList
import co.kr.bemyplan.domain.model.purchase.before.PreviewPlan

interface PreviewRepository {
    suspend fun getPreviewInfo(postId: Int): ResponsePreviewInfo
    suspend fun getPreviewList(postId: Int): ResponsePreviewList

    suspend fun fetchPreviewPlan(planId: Int): PreviewPlan
}