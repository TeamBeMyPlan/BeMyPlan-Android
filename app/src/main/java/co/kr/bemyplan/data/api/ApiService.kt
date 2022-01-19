package co.kr.bemyplan.data.api

import co.kr.bemyplan.BuildConfig
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiService {
    private const val BASE_URL = BuildConfig.BASE_URL

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    // 인기 여행일정 조회
    val homePopularService: HomePopularService = retrofit.create(HomePopularService::class.java)
    // 스크랩한 리스트 조회
    val scrapListService: ScrapListService = retrofit.create(ScrapListService::class.java)
    // 최신 여행 리스트 조회
    val newListService: NewListService = retrofit.create(NewListService::class.java)
    // 여행지 리스트 조회
    val locationService:LocationService=retrofit.create(LocationService::class.java)
    // 추천 여행 리스트 조회
    val suggestListService: SuggestListService = retrofit.create(SuggestListService::class.java)
    // 여행지 여행일정 조회
    val locationListService: LocationListService = retrofit.create(LocationListService::class.java)
    // 유저가 게시한 포스트 조회
    val userPostListService: UserPostListService = retrofit.create(UserPostListService::class.java)
    // 여행 컨텐츠 구매 전 정보 조회
    val previewInfoService: PreviewInfoService = retrofit.create(PreviewInfoService::class.java)
    // 여행 컨텐츠 구매 전 리스트 조회
    val previewListService: PreviewListService = retrofit.create(PreviewListService::class.java)
}