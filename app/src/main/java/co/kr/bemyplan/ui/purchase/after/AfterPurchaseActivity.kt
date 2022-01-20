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

        // network 연결
        initNetwork()

        // 스크롤뷰 설정
        initNestedScrollView()

        setContentView(binding.root)

        // TODO: Kakao Map
        setMap()
    }

    private fun exampleView() {
        val spots: List<List<Spot>> = listOf(listOf(
            Spot("우무", "제주 우뭇가사리로 만든 수제 푸딩인데 공항 근처에 있어서 숙소 가기 전에 들렀어요! 전에 왔을 때는 웨이팅이 조금 있었는데 오후 2시쯤에 가니까 사람이 없어서 바로 구매할 수 있었습니다 ㅎㅎ 맛은 커스터드, 초코, 우도땅콩 푸딩 세 개를 구매했는데 다 너무 맛있었어요. 커스터드 맛은 산뜻하고 적당히 단 맛이라면, 초코랑 우도 땅콩 맛은 재료가 많이 들어갔는지 진한 맛이었습니다.\n" +
                "\n" +
                "인위적인 보존제를 넣지 않아 구입 후 30분 이내로 바로 먹으라고 하는데, 겨울 기준 하루 정도는 실온에 두었다 먹어도 괜찮았어요! 가게 내부 취식이 안되니 정 걱정되시는 분들은 위 주의사항을 유념해서 여행 동선을 조정하시면 좋을 것 같아요.\n" +
                "\n" +
                "그리고 제가 갔을 때만 그랬을 수도 있지만, 지난 여름에 오후 5시쯤에 방문하니 물류가 와서 트럭이 가게 앞을 막더라구요. 가게 앞 포토존에서 사진 찍고 싶으신 분들은 이 점도 유의하시면 좋을 것 같습니다!",
                listOf("https://user-images.githubusercontent.com/63637706/150272515-7488ecc4-2bc0-49f7-aed3-caff5b2f8c27.png"),
                "제주특별자치도 제주시 관덕로8길 40-1", 126.523386649277, 33.5098291165892, "차량", "52분"),
            Spot("당신의 공천포 게스트하우스", "방에서 바다가 보이는 게스트하우스라고 해서 방문했습니다. 이전에 게스트하우스를 방문해본 적이 없어서 막연한 거부감이 들었었는데요. 하지만 여행 기간 내내 이 숙소에 머무르면서 정말 만족했습니다! 아침에 일출을 방 안에서 볼 수 있다는 것만으로도 이 숙소는 충분히 가치가 있다고 생각해요ㅎㅎ 아침 조식도 9시쯤 제공되는데 브런치 양식으로 제공되어서 커피와 함께 여유로운 아침을 시작할 수 있습니다. 단점이 있다면 숙소-도로-바다 순으로 되어 있어서 사람들이 창밖으로 지나다닌다는 점인데, 잘 때나 사생활이 필요할 때 커튼을 닫아두고 생활하면 될 것 같아요. \n" +
                    "\n" +
                    "고양이가 2마리 있습니다! 한 마리는 정말 시크하지만, 다른 고양이는 놀아주면 아주 좋아하고 회 같은 걸 먹으면 옆에 와서 구경할 정도로(실제로 먹으려 하진 않고 구경만 열심히 해요ㅎㅎ )사교적이예요! 그리고 숙소가 올레길 5코스 쪽에 위치하고 있기 때문에 주변을 산책하기도 좋아요! \n" +
                    "\n" +
                    "서귀포시 내에서는 대중교통으로 편하게 움직일 수 있지만, 제주시-서귀포시 간에는 조금 어려워서 택시나 차를 이용하는 것을 추천드려요! (한 번에 가는 버스가 있긴 하지만, 하루에 몇 번 안 지나다녀서 그걸 타는 건 어려울 것 같아요)",
                listOf("https://user-images.githubusercontent.com/63637706/150272598-4c9d4e6f-77b3-4133-ae5d-844579cadd47.png", "https://user-images.githubusercontent.com/63637706/150272605-c5ffcc52-388a-4f9d-9f63-9b97f298fe20.png", "https://user-images.githubusercontent.com/63637706/150272609-edc075b3-4866-4087-872c-95c6c0bdb8ce.png"),
                "제주특별자치도 서귀포시 남원읍 공천포로 63", 126.640907286979, 33.2646258193542, "도보", "3분"),
            Spot("공천포식당", "숙소 바로 옆에 식당이 있어서 방문해봤는데 생각 외로 맛있어서 만족스러웠습니다! 전복 물회와 전복죽을 먹었는데 물회는 100% 된장 베이스라 다른 고추장 베이스 물회랑은 또 다른 묘미더라구요. 물회와 죽 모두 전복이 아낌없이 들어가서 너무 좋았습니다.\n" +
                    "\n" +
                    "오이가 정말! 많이 들어가서 오이를 싫어하시는 분들은 미리 주문할 때 말씀을 드려야 해요.",
            listOf("https://user-images.githubusercontent.com/63637706/150272650-1e98658c-1802-4e9f-9d7f-fd9faec1f8af.png", "https://user-images.githubusercontent.com/63637706/150272653-e8026d05-85a4-40d0-8af5-037f93b0cba0.png"),
            "제주특별자치도 서귀포시 남원읍 공천포로 89", 126.64258544722, 33.266389016263, "도보", "?"),
            Spot("서귀포 매일올레시장", "야식으로 먹을 회포장과 본가로 귤 배달을 시키기 위해 방문했습니다. 유명한 시장이라 그런지 사람들이 많았어요. 저렴하게 회포장하고 가볍게 지인들에게 줄 기성 기념품 구입할 때 방문하는 걸 추천드려요!\n" +
                    "\n" +
                    "회포장과 기성 기념품은 거의 다 가격이 똑같기 때문에 사람 적은 곳으로 가서 빠르게 사는 것을 더 추천합니다\uD83D\uDE0A",
            listOf("https://user-images.githubusercontent.com/63637706/150273440-53790f9c-b1ac-46e4-8a1a-ff97675bbdb3.png"),
            "제주특별자치도 서귀포시 중앙로62번길 18", 126.563230508718, 33.2501489768202, "", "")
        ))
        val data = Post("베이비타이거",
            "친구와 함께 퇴사 기념 힐링여행",
        spots, 3)
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