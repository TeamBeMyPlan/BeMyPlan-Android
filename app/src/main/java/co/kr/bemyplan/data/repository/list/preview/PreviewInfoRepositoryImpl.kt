package co.kr.bemyplan.data.repository.list.preview

import co.kr.bemyplan.data.api.ApiService
import co.kr.bemyplan.data.entity.purchase.before.ResponsePreviewInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PreviewInfoRepositoryImpl : PreviewInfoRepository {
    override suspend fun getPreviewInfo(post_id: Int): ResponsePreviewInfo {
        return withContext(Dispatchers.IO) {
            ApiService.previewInfoService.getPreviewInfo(post_id)
        }
    }
}