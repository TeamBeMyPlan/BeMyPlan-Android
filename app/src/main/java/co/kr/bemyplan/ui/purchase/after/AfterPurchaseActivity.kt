package co.kr.bemyplan.ui.purchase.after

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
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
import co.kr.bemyplan.domain.model.purchase.after.PlanDetail
import co.kr.bemyplan.domain.model.purchase.after.MergedPlanAndInfo
import co.kr.bemyplan.ui.list.ListActivity
import co.kr.bemyplan.ui.purchase.after.viewmodel.AfterPurchaseViewModel
import com.google.android.material.chip.ChipGroup
import dagger.hilt.android.AndroidEntryPoint
import net.daum.mf.map.api.CalloutBalloonAdapter
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView

@AndroidEntryPoint
class AfterPurchaseActivity : AppCompatActivity() {
    private var _binding: ActivityAfterPurchaseBinding? = null
    private val binding get() = _binding ?: error("Binding이 초기화 되지 않았습니다.")

    private val viewModel by viewModels<AfterPurchaseViewModel>()

    private lateinit var mapView: MapView
    private val eventListener = MarkerEventListener(this)
    private var mapPoints = mutableListOf<MapPoint>()
    private var markers = mutableListOf(mutableListOf<MapPOIItem>())

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

//        val author = intent.getStringExtra("author")
//        viewModel.setAuthor(requireNotNull(author))

//        val userId = intent.getIntExtra("userId", 0)
//        val userNickname = intent.getStringExtra("nickname")

        // 카카오맵 초기화
        initMap()
        // kakaomap 터치 이벤트
        initTouchListener()

        // 더미데이터, 진짜데이터 구분
        checkData(planId)

        // Observer
        viewModel.planDetail.observe(this) {
            // fragment 생성
            initFragment(0)
            // 마커 생성
            initMarker(listOf(it.contents[0].spots))
            // 일자별 버튼 생성
            initChips(listOf(it.contents[0].spots))
            // user 버튼 생성
            binding.clWriter.setOnClickListener{ _ -> initUserButton(it) }
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
            viewModel.fetchPlanDetail(planId)
            viewModel.fetchMoveInfo(planId)
        }
    }

    // fragment 그리기
    private fun initFragment(index: Int) {
        viewModel.setSpots(binding.viewModel.contents.value!![index].spots)
        viewModel.setMoveInfo(index)
        val fragment = DailyContentsFragment()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fcv_daily_context, fragment)
            .commit()
    }

    // 작성자 정보 다음 뷰로 전송
    private fun initUserButton(data: PlanDetail) {
        val intent = Intent(this, ListActivity::class.java)
        intent.putExtra("from", "user")
        intent.putExtra("userId", data.user.userId)
        intent.putExtra("nickname", data.user.nickname)
        startActivity(intent)
        finish()
    }

    // 일차별 버튼 초기화
    private fun initChips(data: List<List<MergedPlanAndInfo>>) {
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
    private fun initMarker(data: List<List<MergedPlanAndInfo>>) {
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

            for (j in data[i].indices) {
                val marker = MapPOIItem()
                val spot = data[i][j]
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

    private fun setMarker(index: Int, data: List<List<MergedPlanAndInfo>>) {
        mapView.removeAllPOIItems()
        mapPoints = mutableListOf()
        for(i in data.indices) {
            if (index == i) {
                for (j in data[i].indices) {
                    markers[i][j].apply {
                        itemName = data[i][j].name
                        mapPoint = MapPoint.mapPointWithGeoCoord(data[i][j].latitude, data[i][j].longitude)
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
                for (j in data[i].indices) {
                    markers[i][j].apply {
                        itemName = data[i][j].name
                        mapPoint = MapPoint.mapPointWithGeoCoord(data[i][j].latitude, data[i][j].longitude)
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