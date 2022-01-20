package co.kr.bemyplan.ui.purchase.after

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.NestedScrollView
import androidx.databinding.DataBindingUtil
import co.kr.bemyplan.R
import co.kr.bemyplan.data.api.ApiService
import co.kr.bemyplan.data.entity.purchase.after.Post
import co.kr.bemyplan.data.entity.purchase.after.ResponseAfterPost
import co.kr.bemyplan.data.entity.purchase.after.Spot
import co.kr.bemyplan.databinding.ActivityAfterPurchaseBinding
import co.kr.bemyplan.databinding.ItemDayButtonBinding
import co.kr.bemyplan.ui.list.ListActivity
import co.kr.bemyplan.ui.purchase.after.example.ExampleDummy
import com.google.android.material.chip.ChipGroup
import net.daum.mf.map.api.MapView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AfterPurchaseActivity : AppCompatActivity() {
    private var _binding: ActivityAfterPurchaseBinding? = null
    private val binding get() = _binding ?: error("Binding이 초기화 되지 않았습니다.")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = DataBindingUtil.setContentView(this, R.layout.activity_after_purchase)

        initDummy()
        // network 연결
        //initNetwork()

        // 스크롤뷰 설정
        initNestedScrollView()

        setContentView(binding.root)

        // TODO: Kakao Map
        setMap()
    }

    private fun initDummy() {
        val dummy = ExampleDummy().getPost()
        binding.post = dummy
        initFragment(0)
        // user button
        initUserButton()
        // back button
        initBackButton()
        // 일차별 버튼
        initChips(dummy.spots)
    }

    private fun initNetwork() {
        val call = ApiService.afterPostService.getPost(5)
        call.enqueue(object: Callback<ResponseAfterPost> {
            override fun onResponse(
                call: Call<ResponseAfterPost>,
                response: Response<ResponseAfterPost>
            ) {
                if (response.isSuccessful) {
                    Log.d("hoooni", response.body()?.data.toString())
                    val data = response.body()?.data
                    binding.post = data
                    // fragment 보이기
                    initFragment(0)
                    // user button
                    initUserButton()
                    // back button
                    initBackButton()
                    // 일차별 버튼
                    initChips(binding.post!!.spots)
                }
                else {
                    Log.d("hoooni", "sdf2")
                }
            }

            override fun onFailure(call: Call<ResponseAfterPost>, t: Throwable) {
                Log.d("NetworkTest", "error: $t")
            }
        })
    }

    private fun setMap() {
        val mapView = MapView(this)
        binding.mapView.addView(mapView)
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
            }

            if(i == 0) chip.chipDayButton.isChecked = true
            chipGroup.addView(chip.root)
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
}