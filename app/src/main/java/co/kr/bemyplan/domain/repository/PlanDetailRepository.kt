package co.kr.bemyplan.domain.repository

import co.kr.bemyplan.domain.model.purchase.after.PlanDetail

interface PlanDetailRepository {
    suspend fun fetchPlanDetail(planId: Int): PlanDetail
}