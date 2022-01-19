package co.kr.bemyplan.data.repository.list

import co.kr.bemyplan.data.api.ApiService
import co.kr.bemyplan.data.entity.purchase.before.PreviewInfoModel
import co.kr.bemyplan.data.entity.purchase.before.ResponsePreviewInfo

class PreviewInfoRepositoryImpl: PreviewInfoRepository {
    override suspend fun getPreviewInfo(post_id: Int): ResponsePreviewInfo {
        return ApiService.previewInfoService.getPreviewInfo(post_id)
    }
}