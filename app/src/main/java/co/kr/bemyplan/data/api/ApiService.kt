package co.kr.bemyplan.data.api

import co.kr.bemyplan.BuildConfig
import co.kr.bemyplan.data.entity.main.home.HomePopularService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiService {
    private const val BASE_URL = BuildConfig.BASE_URL

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val homePopularService: HomePopularService = retrofit.create(HomePopularService::class.java)
    // 스크랩한 리스트 조회
    val scrapListService: ScrapListService = retrofit.create(ScrapListService::class.java)
    // 최신 여행 리스트 조회
    val newListService: NewListService = retrofit.create(NewListService::class.java)
    // 추천 여행 리스트 조회
    val suggestListService: SuggestListService = retrofit.create(SuggestListService::class.java)
}