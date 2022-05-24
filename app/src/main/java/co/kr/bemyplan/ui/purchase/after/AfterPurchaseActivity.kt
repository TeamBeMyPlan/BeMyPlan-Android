package co.kr.bemyplan.ui.purchase.after

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.graphics.Rect
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.core.widget.NestedScrollView
import androidx.databinding.DataBindingUtil
import co.kr.bemyplan.R
import co.kr.bemyplan.databinding.ActivityAfterPurchaseBinding
import co.kr.bemyplan.databinding.ItemDayButtonBinding
import co.kr.bemyplan.domain.model.purchase.after.MergedPlanAndInfo
import co.kr.bemyplan.domain.model.purchase.after.Spots
import co.kr.bemyplan.domain.model.purchase.after.SpotsWithAddress
import co.kr.bemyplan.domain.model.purchase.after.toSpotsWithAddress
import co.kr.bemyplan.ui.list.ListActivity
import co.kr.bemyplan.ui.purchase.after.viewmodel.AfterPurchaseViewModel
import com.google.android.material.chip.ChipGroup
import com.kakao.sdk.common.KakaoSdk.appKey
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import kotlinx.coroutines.internal.synchronized
import net.daum.mf.map.api.*
import timber.log.Timber
import java.util.concurrent.atomic.AtomicReference
import java.util.concurrent.locks.Lock
import java.util.concurrent.locks.ReentrantLock

@AndroidEntryPoint
class AfterPurchaseActivity : AppCompatActivity() {
    private var _binding: ActivityAfterPurchaseBinding? = null
    private val binding get() = _binding ?: error("Binding이 초기화 되지 않았습니다.")

    private val viewModel by viewModels<AfterPurchaseViewModel>()

    private lateinit var mapView: MapView
    private val eventListener = MarkerEventListener(this)
    private var mapPoints = mutableListOf<MapPoint>()
    private var markers = mutableListOf(mutableListOf<MapPOIItem>())
    // 좌표 -> 주소로 바꿀 때 쓸 리스트
    private val addressList = mutableListOf(mutableListOf<SpotsWithAddress>())
    // 모든 주소가 다 들어왔을때 통신이 이루어지게 하기 위함
    private var addressCount = 0
    private var dayCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // binding
        _binding = DataBindingUtil.setContentView(this, R.layout.activity_after_purchase)

        // viewmodel 설정
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        // plan id 받아오기
        val planId = intent.getIntExtra("postId", -1)
        viewModel.setPlanId(planId)

        var isScraped = intent.getBooleanExtra("scrapStatus", false)
        viewModel.setIsScraped(isScraped)

        val authorNickname = intent.getStringExtra("authorNickname") ?: ""
        val authorUserId = intent.getIntExtra("authorUserId", -1)
        viewModel.setAuthor(authorNickname, authorUserId)

        // 카카오맵 초기화
        initMap()
        // kakaomap 터치 이벤트
        initTouchListener()

        // 더미데이터, 진짜데이터 구분
        checkData(planId)

        // Observer
        viewModel.mergedPlanAndInfoList.observe(this) {
            // fragment 생성
            initFragment(0)
            // 마커 생성
            initMarker(it)
            // 일자별 버튼 생성
            initChips(it)
        }

        viewModel.planDetail.observe(this) {
            setAddressFromKakao()
            //viewModel.setMergedPlanAndInfoList(viewModel.planDetail.value!!, viewModel.moveInfoList.value!!)
            // writer 버튼 생성
            binding.clWriter.setOnClickListener { initUserButton() }
        }

        // back button
        binding.ivBack.setOnClickListener { finish() }
        // 스크롤뷰 설정
        binding.svDailyContents.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { _, _, _, _, _ -> setTopTitle() })

        setContentView(binding.root)
    }

    // 더미데이터인지, 진짜데이터인지 확인
    private fun checkData(planId: Int) {
        if (planId == -1) {
            viewModel.initDummy()
            binding.ivToWriterProfile.isVisible = false

            // fragment 보이기
            initFragment(0)
        } else { // network 연결
            viewModel.fetchMoveInfo(planId)
        }
    }

    // fragment 그리기
    private fun initFragment(index: Int) {
        viewModel.setSpots(index)
        viewModel.setMoveInfo(index)

        val fragment = DailyContentsFragment()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fcv_daily_context, fragment)
            .commit()
    }

    private fun getAddressFromGeoCode(mapPoint: MapPoint) {
        val ai: ApplicationInfo = packageManager.getApplicationInfo(
            packageName,
            PackageManager.GET_META_DATA
        )
        if (ai.metaData != null) {
            val metaData: String? = ai.metaData.getString("com.kakao.sdk.AppKey")
            mapPoint.let {
                val currentMapPoint =  MapPoint.mapPointWithGeoCoord(mapPoint.mapPointGeoCoord.latitude, mapPoint.mapPointGeoCoord.longitude)
                MapReverseGeoCoder(metaData, currentMapPoint, object : MapReverseGeoCoder.ReverseGeoCodingResultListener {
                    override fun onReverseGeoCoderFoundAddress(p0: MapReverseGeoCoder?, address: String) {
                        // 주소 받아오기 성공 - address: 현재 주소
                        viewModel.spots.value?.get(addressCount)?.let {
                            addressList[dayCount].add(it.toSpotsWithAddress(address))
                        }
                        ++addressCount

                        if (addressCount == viewModel.mergedPlanAndInfo.value?.infos?.size) {
                            if (dayCount == viewModel.mergedPlanAndInfoList.value?.size) {
                                viewModel.setMergedPlanAndInfoList(viewModel.planDetail.value!!, viewModel.moveInfoList.value!!)
                                Timber.tag("hooni").d(addressList.toString())
                            }
                            ++dayCount
                            addressCount = 0
                        }
                    }
                    override fun onReverseGeoCoderFailedToFindAddress(p0: MapReverseGeoCoder?) {
                        // 주소 받아오기 실패
                        Timber.tag("MapReverseGeoCoder").d("Can't get address from map point")
                    }
                }, this).startFindingAddress()
            }
        }
    }

    private fun setAddressFromKakao() {
        val spotList = viewModel.mergedPlanAndInfoList.value
        if (spotList != null) {
            for (spots in spotList) {
                for (spot in spots.infos) {
                    getAddressFromGeoCode(
                        MapPoint.mapPointWithGeoCoord(
                            spot.second.latitude,
                            spot.second.longitude
                        )
                    )
                }
            }
        }
    }

    // 작성자 정보 다음 뷰로 전송
    private fun initUserButton() {
        val intent = Intent(this, ListActivity::class.java)
        intent.putExtra("from", "user")
        intent.putExtra("authorNickname", viewModel.authorNickname)
        intent.putExtra("authorUserId", viewModel.authorUserId)
        startActivity(intent)
        finish()
    }

    // 일차별 버튼 초기화
    private fun initChips(data: List<MergedPlanAndInfo>) {
        val chipGroup: ChipGroup = binding.chipGroupDay
        for (i in data.indices) {
            val chip = ItemDayButtonBinding.inflate(layoutInflater)
            chip.root.id = View.generateViewId()
            chip.position = i

            chip.chipDayButton.setOnClickListener {
                initFragment(i)
                setMarker(i, data)
                mapView.setMapCenterPoint(mapPoints[0], true)
                mapView.fitMapViewAreaToShowMapPoints(mapPoints.toTypedArray())
            }

            if(i == 0) {
                chip.chipDayButton.isChecked = true
                setMarker(i, data)
                mapView.setMapCenterPoint(mapPoints[i], true)
                mapView.fitMapViewAreaToShowMapPoints(mapPoints.toTypedArray())
            }
            chipGroup.addView(chip.root)
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initTouchListener() {
        binding.mapView.getChildAt(0).setOnTouchListener { _, _ ->
            binding.svDailyContents.requestDisallowInterceptTouchEvent(true)
            false
        }
    }

    // 툴바를 제외한 acitiviy에 제목이 보이지 않으면 제목이 툴바에서 보이고, 제목이 보이면 툴바에서 제목이 보이지 않도록 설정
    private fun setTopTitle() {
        val rect = Rect()
        binding.svDailyContents.getHitRect(rect)
        if (binding.tvTitle.getLocalVisibleRect(rect)) {
            // view가 보이는 경우
            binding.tvTopTitle.visibility = View.INVISIBLE
        } else {
            // view가 안 보이는 경우
            binding.tvTopTitle.visibility = View.VISIBLE
        }
    }

    // 카카오맵 초기화
    private fun initMap() {
        mapView = MapView(this)

        // 커스텀 말풍선 등록
        mapView.setCalloutBalloonAdapter(CustomBalloonAdapter(layoutInflater))
        // 마커 클릭 이벤트 리스너 등록
        mapView.setPOIItemEventListener(eventListener)

        binding.mapView.addView(mapView)
    }

    // 카카오맵의 핀 초기화
    private fun initMarker(data: List<MergedPlanAndInfo>) {
        markers = mutableListOf()
        for (i in data.indices) {
            /*
            * 현재 일차의 위도, 경도 합을 구함
            * 합을 size로 나누어서 중심 위치 구함
            * 중심 위치가 현 지도의 center point
            * */
            var sumLatitude = 0.0
            var sumLongitude = 0.0

            val markerList = mutableListOf<MapPOIItem>()

            for (j in data[i].infos.indices) {
                val marker = MapPOIItem()
                val spot = data[i].infos[j].second
                sumLatitude += spot.latitude
                sumLongitude += spot.longitude

                marker.apply {
                    itemName = spot.name
                    mapPoint = MapPoint.mapPointWithGeoCoord(spot.latitude, spot.longitude)
                    markerType = MapPOIItem.MarkerType.CustomImage
                    customImageResourceId = R.drawable.icn_subpin_select
                    selectedMarkerType = MapPOIItem.MarkerType.CustomImage
                    customSelectedImageResourceId = R.drawable.icn_subpin_select
                    isCustomImageAutoscale = false
                    setCustomImageAnchor(1.0f, 1.0f)
                }

                markerList.add(marker)

                if (i == 0) mapPoints.add(marker.mapPoint)

                mapView.addPOIItem(marker)
                mapView.fitMapViewAreaToShowMapPoints(mapPoints.toTypedArray())
            }

            markers.add(markerList)
        }
    }

    private fun setMarker(index: Int, data: List<MergedPlanAndInfo>) {
        mapView.removeAllPOIItems()
        mapPoints = mutableListOf()
        for(i in data.indices) {
            if (index == i) {
                for (j in data[i].infos.indices) {
                    markers[i][j].apply {
                        itemName = data[i].infos[j].second.name
                        mapPoint = MapPoint.mapPointWithGeoCoord(data[i].infos[j].second.latitude, data[i].infos[j].second.longitude)
                        markerType = MapPOIItem.MarkerType.CustomImage
                        selectedMarkerType = MapPOIItem.MarkerType.CustomImage
                        customImageResourceId = R.drawable.icn_mainpin_select
                        customSelectedImageResourceId = R.drawable.icn_mainpin_select_pick
                        isShowCalloutBalloonOnTouch = true
                        setCustomImageAnchor(0.5f, 1.0f)
                    }
                    mapPoints.add(markers[i][j].mapPoint)
                }
                mapView.fitMapViewAreaToShowMapPoints(mapPoints.toTypedArray())
            }
            else {
                for (j in data[i].infos.indices) {
                    markers[i][j].apply {
                        itemName = data[i].infos[j].second.name
                        mapPoint = MapPoint.mapPointWithGeoCoord(data[i].infos[j].second.latitude, data[i].infos[j].second.longitude)
                        markerType = MapPOIItem.MarkerType.CustomImage
                        selectedMarkerType = MapPOIItem.MarkerType.CustomImage
                        customImageResourceId = R.drawable.icn_subpin_select
                        customSelectedImageResourceId = R.drawable.icn_subpin_select
                        isShowCalloutBalloonOnTouch = false
                        setCustomImageAnchor(1.0f, 1.0f)
                    }
                }
            }
        }
        for (i in markers.indices) {
            for (j in markers[i].indices) {
                mapView.addPOIItem(markers[i][j])
            }
        }
    }

    // 커스텀 말풍선 클래스
    class CustomBalloonAdapter(private val inflater: LayoutInflater): CalloutBalloonAdapter {
        private val mCalloutBalloon = inflater.inflate(R.layout.card_balloon, null)
        val name: TextView = mCalloutBalloon.findViewById(R.id.tv_title)

        override fun getCalloutBalloon(poiItem: MapPOIItem?): View {
            // 마커 클릭 시 나오는 말풍선
            name.text = poiItem?.itemName
            return mCalloutBalloon
        }

        override fun getPressedCalloutBalloon(poiItem: MapPOIItem?): View {
            // 말풍선 클릭 시
            mCalloutBalloon.setOnClickListener {
                val intentKakaoMap = inflater.context.packageManager.getLaunchIntentForPackage("net.daum.android.map")
                try {
                    val latitude = poiItem?.mapPoint?.mapPointGeoCoord?.latitude
                    val longitude = poiItem?.mapPoint?.mapPointGeoCoord?.longitude
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse("kakaomap://look?p=$latitude,$longitude"))
                    inflater.context.startActivity(intent)
                } catch (e: Exception) {
                    val intentPlayStore = Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$intentKakaoMap"))
                    inflater.context.startActivity(intentPlayStore)
                }
            }

            return mCalloutBalloon
        }
    }

    // 마커 클릭 이벤트 리스너
    class MarkerEventListener(val context: Context): MapView.POIItemEventListener {
        override fun onPOIItemSelected(mapView: MapView?, poiItem: MapPOIItem?) {
            // 마커 클릭 시
        }

        override fun onCalloutBalloonOfPOIItemTouched(mapView: MapView?, poiItem: MapPOIItem?) {
            // 말풍선 클릭 시 (Deprecated)
        }

        override fun onCalloutBalloonOfPOIItemTouched(mapView: MapView?, poiItem: MapPOIItem?, buttonType: MapPOIItem.CalloutBalloonButtonType?) {
            // 말풍선 클릭 시
            val intentKakaoMap = context.packageManager.getLaunchIntentForPackage("net.daum.android.map")
            try {
                val latitude = poiItem?.mapPoint?.mapPointGeoCoord?.latitude
                val longitude = poiItem?.mapPoint?.mapPointGeoCoord?.longitude
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("kakaomap://look?p=$latitude,$longitude"))
                context.startActivity(intent)
            } catch (e: Exception) {
                val intentPlayStore = Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$intentKakaoMap"))
                context.startActivity(intentPlayStore)
            }
        }

        override fun onDraggablePOIItemMoved(mapView: MapView?, poiItem: MapPOIItem?, mapPoint: MapPoint?) {
            // 마커의 속성 중 isDraggable = true 일 때 마커를 이동시켰을 경우
        }
    }
}