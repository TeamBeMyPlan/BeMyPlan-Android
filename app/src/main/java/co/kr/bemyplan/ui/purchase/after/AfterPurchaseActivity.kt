package co.kr.bemyplan.ui.purchase.after

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Rect
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.NestedScrollView
import androidx.databinding.DataBindingUtil
import co.kr.bemyplan.R
import co.kr.bemyplan.data.api.ApiService
import co.kr.bemyplan.data.entity.purchase.after.ResponseAfterPost
import co.kr.bemyplan.data.entity.purchase.after.Spot
import co.kr.bemyplan.databinding.ActivityAfterPurchaseBinding
import co.kr.bemyplan.databinding.ItemDayButtonBinding
import co.kr.bemyplan.ui.list.ListActivity
import co.kr.bemyplan.ui.purchase.after.example.ExampleDummy
import com.google.android.material.chip.ChipGroup
import net.daum.mf.map.api.CalloutBalloonAdapter
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class AfterPurchaseActivity : AppCompatActivity() {
    private var _binding: ActivityAfterPurchaseBinding? = null
    private val binding get() = _binding ?: error("Binding이 초기화 되지 않았습니다.")

    private lateinit var mapView: MapView
    private val eventListener = MarkerEventListener(this)
    private var mapPoints = mutableListOf<MapPoint>()
    private var markers = mutableListOf(mutableListOf<MapPOIItem>())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = DataBindingUtil.setContentView(this, R.layout.activity_after_purchase)
        val postId = intent.getIntExtra("postId", -1)

        // 카카오맵
        initMap()

        if (postId == -1) {
            initDummy()
        } else { // network 연결
            initNetwork(postId)
        }

        // back button
        initBackButton()
        // 스크롤뷰 설정
        initNestedScrollView()

        setContentView(binding.root)
    }

    private fun initDummy() {
        val dummy = ExampleDummy().getPost()
        binding.post = dummy
        // 카카오맵
        initMap()
        // fragment 보이기
        initFragment(0)
        // user button
        initUserButton()
        // 일차별 버튼
        initChips(dummy.spots)
    }

    private fun initNetwork(postId: Int) {
        val call = ApiService.afterPostService.getPost(postId)
        call.enqueue(object : Callback<ResponseAfterPost> {
            override fun onResponse(
                call: Call<ResponseAfterPost>,
                response: Response<ResponseAfterPost>
            ) {
                if (response.isSuccessful) {
                    val data = response.body()?.data
                    data?.let {
                        binding.post = it
                        // 마커 생성
                        initMarker(it.spots)
                        // 일차별 버튼
                        initChips(it.spots)
                    }
                    // kakaomap 터치 이벤
                    initTouchListener()
                    // fragment 보이기
                    initFragment(0)
                    // user button
                    initUserButton()
                }
            }

            override fun onFailure(call: Call<ResponseAfterPost>, t: Throwable) {
                Log.d("NetworkTest", "error: $t")
            }
        })
    }

    private fun initFragment(index: Int) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        val fragment = DailyContentsFragment(binding.post!!.spots[index])
        fragmentTransaction.replace(R.id.fcv_daily_context, fragment)
        fragmentTransaction.commit()
    }

    private fun initUserButton() {
        binding.clWriter.setOnClickListener {
            val intent = Intent(this, ListActivity::class.java)
            intent.putExtra("from", "user")
            startActivity(intent)
        }
    }

    private fun initBackButton() {
        binding.ivBack.setOnClickListener {
            finish()
        }
    }

    @SuppressLint("ResourceType")
    private fun initChips(data: List<List<Spot>>) {
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

    private fun initNestedScrollView() {
        binding.svDailyContents.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { _, _, _, _, _ ->
            setTopTitle()
        })
    }

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

    private fun initMap() {
        mapView = MapView(this)

        // 커스텀 말풍선 등록
        mapView.setCalloutBalloonAdapter(CustomBalloonAdapter(layoutInflater))
        // 마커 클릭 이벤트 리스너 등록
        mapView.setPOIItemEventListener(eventListener)

        binding.mapView.addView(mapView)
    }

    private fun initMarker(data: List<List<Spot>>) {
        markers = mutableListOf()
        for (i in data.indices) {
            /*
            * 현재 일차의 위도, 경도 합을 구함
            * 합을 size로 나누어서 중심 위치 구함
            * 중심 위치가 현 지도의 center point
            * */
            var sumLatitude = 0.0
            var sumLongitude = 0.0

            var markerList = mutableListOf<MapPOIItem>()

            for (j in data[i].indices) {
                val marker = MapPOIItem()
                val spot = data[i][j]
                sumLatitude += spot.latitude
                sumLongitude += spot.longitude

                marker.apply {
                    itemName = spot.title
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

    private fun setMarker(index: Int, data: List<List<Spot>>) {
        mapView.removeAllPOIItems()
        mapPoints = mutableListOf()
        for(i in data.indices) {
            if (index == i) {
                for (j in data[i].indices) {
                    markers[i][j].apply {
                        itemName = data[i][j].title
                        mapPoint = MapPoint.mapPointWithGeoCoord(data[i][j].latitude, data[i][j].longitude)
                        markerType = MapPOIItem.MarkerType.CustomImage
                        selectedMarkerType = MapPOIItem.MarkerType.CustomImage
                        customImageResourceId = R.drawable.icn_mainpin_select
                        customSelectedImageResourceId = R.drawable.icn_mainpin_select_pick
                        isShowCalloutBalloonOnTouch = true
                        setCustomImageAnchor(1.0f, 1.0f)
                    }
                    mapPoints.add(markers[i][j].mapPoint)
                }
                mapView.fitMapViewAreaToShowMapPoints(mapPoints.toTypedArray())
            }
            else {
                for (j in data[i].indices) {
                    markers[i][j].apply {
                        itemName = data[i][j].title
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
        private val mCalloutBalloon: View = inflater.inflate(R.layout.card_balloon, null)
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