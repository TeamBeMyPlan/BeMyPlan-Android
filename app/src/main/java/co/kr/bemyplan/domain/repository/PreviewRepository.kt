package co.kr.bemyplan.domain.repository

import co.kr.bemyplan.domain.model.purchase.before.PreviewPlan

interface PreviewRepository {
    suspend fun fetchPreviewPlan(planId: Int): PreviewPlan
}