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

    // 여행지 리스트 조회
    val locationService: LocationService = retrofit.create(LocationService::class.java)

    // 구매 후 포스트 조회
    val afterPostService: AfterPostService = retrofit.create(AfterPostService::class.java)

    // 최신 여행 일정 조회
    val homeNewService: HomeNewService = retrofit.create(HomeNewService::class.java)

    // 에디터 추천 여행 일정 조회
    val homeSuggestService: HomeSuggestService = retrofit.create(HomeSuggestService::class.java)

    // 회원 탈퇴
    val withdrawService: WithdrawService = retrofit.create(WithdrawService::class.java)

    // 스크랩 POST
    val postScrapService: PostScrapService = retrofit.create(PostScrapService::class.java)
}