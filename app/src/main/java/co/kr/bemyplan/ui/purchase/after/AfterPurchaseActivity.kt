package co.kr.bemyplan.ui.purchase.after

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import co.kr.bemyplan.R
import co.kr.bemyplan.data.Post
import co.kr.bemyplan.databinding.ActivityAfterPurchaseBinding
import net.daum.mf.map.api.MapView

class AfterPurchaseActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAfterPurchaseBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_after_purchase)

        // data 객체 생성
        binding.post = Post("감성을 느낄 수 있는 힐링여행", "thisisuzzwon", 4)

        setContentView(binding.root)

        // Kakao Map 세팅
        //setMap()

        // 액션바 숨기기
        supportActionBar?.hide()
    }

    private fun setMap() {
        val mapView = MapView(this)
        binding.mapView.addView(mapView)
    }
}