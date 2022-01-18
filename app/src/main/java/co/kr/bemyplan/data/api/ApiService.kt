package co.kr.bemyplan.data.api

import co.kr.bemyplan.BuildConfig
import co.kr.bemyplan.data.entity.main.home.HomePopularService
import co.kr.bemyplan.data.entity.main.scrap.ScrapListService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiService {
    private const val BASE_URL = BuildConfig.BASE_URL

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val homePopularService: HomePopularService = retrofit.create(HomePopularService::class.java)
    // 스크랩 리스트 불러오기
    val scrapListService: ScrapListService = retrofit.create(ScrapListService::class.java)
}