package co.kr.bemyplan.data.repository.purchase.preview

import co.kr.bemyplan.data.api.PreviewInfoService
import co.kr.bemyplan.data.entity.purchase.before.ResponsePreviewInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PreviewInfoRepositoryImpl @Inject constructor(
    private val previewInfoService: PreviewInfoService
) : PreviewInfoRepository {
    override suspend fun getPreviewInfo(postId: Int): ResponsePreviewInfo {
        return withContext(Dispatchers.IO) {
            previewInfoService.getPreviewInfo(postId)
        }
    }
}