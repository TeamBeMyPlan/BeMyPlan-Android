package co.kr.bemyplan.ui.purchase.after

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import co.kr.bemyplan.data.entity.purchase.after.DailyContents
import co.kr.bemyplan.data.entity.purchase.after.Spot
import co.kr.bemyplan.databinding.FragmentDailyContentsBinding
import co.kr.bemyplan.ui.purchase.after.adapter.DailyContentsAdapter

class DailyContentsFragment(private val spotList: List<Spot>) : Fragment() {
    private lateinit var contentsAdapter: DailyContentsAdapter
    private lateinit var routeAdapter: DailyContentsAdapter
    private var _binding: FragmentDailyContentsBinding? = null
    private val binding get() = _binding ?: error("Binding이 초기화 되지 않았습니다.")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDailyContentsBinding.inflate(layoutInflater, container, false)
        initAdapter()

        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun initAdapter() {
        contentsAdapter = DailyContentsAdapter(DailyContentsAdapter.TYPE_CONTENTS)
        routeAdapter = DailyContentsAdapter(DailyContentsAdapter.TYPE_ROUTE)

//        val items = listOf(
//            Spot("산방산","제주특별자치도 제주시 애월읍 유수암리 산138", listOf("https://picsum.photos/300/400/?random", "https://picsum.photos/300/400/?random", "https://picsum.photos/300/400/?random"), "가볍게 오를만한 산이에요. 제가 갔을 때 산방산은 입산이 금지되어 있어서 산방굴사 까지만 오를 수 있었습니다 . 산을 좋아하고 자연을 느끼고 싶은 분들한테 추천드려요 \uD83C\uDF33\n" +
//                    "        \n\n용머리해안 코스 / 산방굴사 코스가 나뉘어져 있으니 미리 검색해보고 가시면 좋아요. 각각 용머리해안이 보이는 경치 코스와 산방굴사가 있는 코스인데 유료입장이에요. 근데 날씨 영향을 많이 받아서 아쉬워요\uD83D\uDE2D 저희가 갔을때는 날이 흐려서 산방산에서 용머리 해안 경치가 잘 안보여서 산방굴사 코스를 선택할 수 밖에 없었어요 ,, 그리고 산방산 구경 후 용머리해안에 가보고 싶었는데 날씨 문제로 입장이 제한됐어요 .\n" +
//                    "        \n\n그래서 산방산에 가고싶은 분들은 여행일정 중 맑은날에 가시는걸 추천드려요 ! 마음처럼 되지는 않겠지만 오늘 날씨 진짜 좋은데 ? 싶으면 다음날 일정이랑 체인지를 해서라도 맑은날에 방문하시는게 베스트 일듯 해요",
//                "도보", "난드르바당", 3, true, false),
//            Spot("난드르바당", "제주특별자치도 서귀포시 안덕면 산방로 141", listOf("https://picsum.photos/300/400/?random"), "산방산에서 걸어서 갈 수 있는 카페예요 ! 산방산이 바로 앞에 보이는 뷰와 넓은 내부가 장점입니다. 좌석이 많은데도 사람이 꽤 많아서 그 점은 조금 아쉬웠어요 . 그치만 산방산에 가셨다면 꼭 방문해 보시길 추천드려요 \uD83D\uDE42 간단한 브런치류와 커피, 음료 등이 있어서 취향껏 선택할 수 있어요. \n" +
//                    "\n" +
//                    "날이 좋다면 외부 좌석에 앉으세요 ! 내부에 좌석이 더 많긴 하지만 테라스 좌석에 앉으면 산방산을 보며 여유롭게 커피 마실 수 있답니다 ㅎㅎ 또, 흐린날에 방문해서 산 오르긴 싫은데 산방산은 보고 싶다면  그냥 원앤온리 제주에 가세요 .. 올라가서 보는 경치는 안 보여도 어차피 산방산 입산이 제한되어 있어서 여기 카페에서 보는 산방산만으로도 만족하실 수 있으실거예요 ! ",
//                "버스", "카페 C.hill", 15, false, false),
//            Spot("카페 C.hill","제주특별자치도 제주시 애월읍 유수암리 산138", listOf("https://picsum.photos/300/400/?random"), "가볍게 오를만한 산이에요. 제가 갔을 때 산방산은 입산이 금지되어 있어서 산방굴사 까지만 오를 수 있었습니다 . 산을 좋아하고 자연을 느끼고 싶은 분들한테 추천드려요 \uD83C\uDF33\n" +
//                    "        \n\n용머리해안 코스 / 산방굴사 코스가 나뉘어져 있으니 미리 검색해보고 가시면 좋아요. 각각 용머리해안이 보이는 경치 코스와 산방굴사가 있는 코스인데 유료입장이에요. 근데 날씨 영향을 많이 받아서 아쉬워요\uD83D\uDE2D 저희가 갔을때는 날이 흐려서 산방산에서 용머리 해안 경치가 잘 안보여서 산방굴사 코스를 선택할 수 밖에 없었어요 ,, 그리고 산방산 구경 후 용머리해안에 가보고 싶었는데 날씨 문제로 입장이 제한됐어요 .\n" +
//                    "        \n\n그래서 산방산에 가고싶은 분들은 여행일정 중 맑은날에 가시는걸 추천드려요 ! 마음처럼 되지는 않겠지만 오늘 날씨 진짜 좋은데 ? 싶으면 다음날 일정이랑 체인지를 해서라도 맑은날에 방문하시는게 베스트 일듯 해요",
//                "자가용", "한라서적타운", 20, false, false),
//            Spot("한라서적타운","제주특별자치도 제주시 애월읍 유수암리 산138", listOf("https://picsum.photos/300/400/?random"), "가볍게 오를만한 산이에요. 제가 갔을 때 산방산은 입산이 금지되어 있어서 산방굴사 까지만 오를 수 있었습니다 . 산을 좋아하고 자연을 느끼고 싶은 분들한테 추천드려요 \uD83C\uDF33\n" +
//                    "        \n\n용머리해안 코스 / 산방굴사 코스가 나뉘어져 있으니 미리 검색해보고 가시면 좋아요. 각각 용머리해안이 보이는 경치 코스와 산방굴사가 있는 코스인데 유료입장이에요. 근데 날씨 영향을 많이 받아서 아쉬워요\uD83D\uDE2D 저희가 갔을때는 날이 흐려서 산방산에서 용머리 해안 경치가 잘 안보여서 산방굴사 코스를 선택할 수 밖에 없었어요 ,, 그리고 산방산 구경 후 용머리해안에 가보고 싶었는데 날씨 문제로 입장이 제한됐어요 .\n" +
//                    "        \n\n그래서 산방산에 가고싶은 분들은 여행일정 중 맑은날에 가시는걸 추천드려요 ! 마음처럼 되지는 않겠지만 오늘 날씨 진짜 좋은데 ? 싶으면 다음날 일정이랑 체인지를 해서라도 맑은날에 방문하시는게 베스트 일듯 해요",
//                "도보", "미친부엌", 10, false, false),
//            Spot("미친부엌","제주특별자치도 제주시 애월읍 유수암리 산138", listOf("https://picsum.photos/300/400/?random"), "가볍게 오를만한 산이에요. 제가 갔을 때 산방산은 입산이 금지되어 있어서 산방굴사 까지만 오를 수 있었습니다 . 산을 좋아하고 자연을 느끼고 싶은 분들한테 추천드려요 \uD83C\uDF33\n" +
//                    "        \n\n용머리해안 코스 / 산방굴사 코스가 나뉘어져 있으니 미리 검색해보고 가시면 좋아요. 각각 용머리해안이 보이는 경치 코스와 산방굴사가 있는 코스인데 유료입장이에요. 근데 날씨 영향을 많이 받아서 아쉬워요\uD83D\uDE2D 저희가 갔을때는 날이 흐려서 산방산에서 용머리 해안 경치가 잘 안보여서 산방굴사 코스를 선택할 수 밖에 없었어요 ,, 그리고 산방산 구경 후 용머리해안에 가보고 싶었는데 날씨 문제로 입장이 제한됐어요 .\n" +
//                    "        \n\n그래서 산방산에 가고싶은 분들은 여행일정 중 맑은날에 가시는걸 추천드려요 ! 마음처럼 되지는 않겠지만 오늘 날씨 진짜 좋은데 ? 싶으면 다음날 일정이랑 체인지를 해서라도 맑은날에 방문하시는게 베스트 일듯 해요",
//                null, "집", 15, false, false),
//            Spot("산방산","제주특별자치도 제주시 애월읍 유수암리 산138", listOf("https://picsum.photos/300/400/?random"), "가볍게 오를만한 산이에요. 제가 갔을 때 산방산은 입산이 금지되어 있어서 산방굴사 까지만 오를 수 있었습니다 . 산을 좋아하고 자연을 느끼고 싶은 분들한테 추천드려요 \uD83C\uDF33\n" +
//                    "        \n\n용머리해안 코스 / 산방굴사 코스가 나뉘어져 있으니 미리 검색해보고 가시면 좋아요. 각각 용머리해안이 보이는 경치 코스와 산방굴사가 있는 코스인데 유료입장이에요. 근데 날씨 영향을 많이 받아서 아쉬워요\uD83D\uDE2D 저희가 갔을때는 날이 흐려서 산방산에서 용머리 해안 경치가 잘 안보여서 산방굴사 코스를 선택할 수 밖에 없었어요 ,, 그리고 산방산 구경 후 용머리해안에 가보고 싶었는데 날씨 문제로 입장이 제한됐어요 .\n" +
//                    "        \n\n그래서 산방산에 가고싶은 분들은 여행일정 중 맑은날에 가시는걸 추천드려요 ! 마음처럼 되지는 않겠지만 오늘 날씨 진짜 좋은데 ? 싶으면 다음날 일정이랑 체인지를 해서라도 맑은날에 방문하시는게 베스트 일듯 해요",
//                "도보", "난드르바당", 3, false, false),
//            Spot("난드르바당", "제주특별자치도 서귀포시 안덕면 산방로 141", listOf("https://picsum.photos/300/400/?random"), "산방산에서 걸어서 갈 수 있는 카페예요 ! 산방산이 바로 앞에 보이는 뷰와 넓은 내부가 장점입니다. 좌석이 많은데도 사람이 꽤 많아서 그 점은 조금 아쉬웠어요 . 그치만 산방산에 가셨다면 꼭 방문해 보시길 추천드려요 \uD83D\uDE42 간단한 브런치류와 커피, 음료 등이 있어서 취향껏 선택할 수 있어요. \n" +
//                    "\n" +
//                    "날이 좋다면 외부 좌석에 앉으세요 ! 내부에 좌석이 더 많긴 하지만 테라스 좌석에 앉으면 산방산을 보며 여유롭게 커피 마실 수 있답니다 ㅎㅎ 또, 흐린날에 방문해서 산 오르긴 싫은데 산방산은 보고 싶다면  그냥 원앤온리 제주에 가세요 .. 올라가서 보는 경치는 안 보여도 어차피 산방산 입산이 제한되어 있어서 여기 카페에서 보는 산방산만으로도 만족하실 수 있으실거예요 ! ",
//                "버스", "카페 C.hill", 15, false, false),
//            Spot("카페 C.hill","제주특별자치도 제주시 애월읍 유수암리 산138", listOf("https://picsum.photos/300/400/?random"), "가볍게 오를만한 산이에요. 제가 갔을 때 산방산은 입산이 금지되어 있어서 산방굴사 까지만 오를 수 있었습니다 . 산을 좋아하고 자연을 느끼고 싶은 분들한테 추천드려요 \uD83C\uDF33\n" +
//                    "        \n\n용머리해안 코스 / 산방굴사 코스가 나뉘어져 있으니 미리 검색해보고 가시면 좋아요. 각각 용머리해안이 보이는 경치 코스와 산방굴사가 있는 코스인데 유료입장이에요. 근데 날씨 영향을 많이 받아서 아쉬워요\uD83D\uDE2D 저희가 갔을때는 날이 흐려서 산방산에서 용머리 해안 경치가 잘 안보여서 산방굴사 코스를 선택할 수 밖에 없었어요 ,, 그리고 산방산 구경 후 용머리해안에 가보고 싶었는데 날씨 문제로 입장이 제한됐어요 .\n" +
//                    "        \n\n그래서 산방산에 가고싶은 분들은 여행일정 중 맑은날에 가시는걸 추천드려요 ! 마음처럼 되지는 않겠지만 오늘 날씨 진짜 좋은데 ? 싶으면 다음날 일정이랑 체인지를 해서라도 맑은날에 방문하시는게 베스트 일듯 해요",
//                "자가용", "한라서적타운", 20, false, false),
//            Spot("한라서적타운","제주특별자치도 제주시 애월읍 유수암리 산138", listOf("https://picsum.photos/300/400/?random"), "가볍게 오를만한 산이에요. 제가 갔을 때 산방산은 입산이 금지되어 있어서 산방굴사 까지만 오를 수 있었습니다 . 산을 좋아하고 자연을 느끼고 싶은 분들한테 추천드려요 \uD83C\uDF33\n" +
//                    "        \n\n용머리해안 코스 / 산방굴사 코스가 나뉘어져 있으니 미리 검색해보고 가시면 좋아요. 각각 용머리해안이 보이는 경치 코스와 산방굴사가 있는 코스인데 유료입장이에요. 근데 날씨 영향을 많이 받아서 아쉬워요\uD83D\uDE2D 저희가 갔을때는 날이 흐려서 산방산에서 용머리 해안 경치가 잘 안보여서 산방굴사 코스를 선택할 수 밖에 없었어요 ,, 그리고 산방산 구경 후 용머리해안에 가보고 싶었는데 날씨 문제로 입장이 제한됐어요 .\n" +
//                    "        \n\n그래서 산방산에 가고싶은 분들은 여행일정 중 맑은날에 가시는걸 추천드려요 ! 마음처럼 되지는 않겠지만 오늘 날씨 진짜 좋은데 ? 싶으면 다음날 일정이랑 체인지를 해서라도 맑은날에 방문하시는게 베스트 일듯 해요",
//                "도보", "미친부엌", 10, false, false),
//            Spot("미친부엌","제주특별자치도 제주시 애월읍 유수암리 산138", listOf("https://picsum.photos/300/400/?random"), "가볍게 오를만한 산이에요. 제가 갔을 때 산방산은 입산이 금지되어 있어서 산방굴사 까지만 오를 수 있었습니다 . 산을 좋아하고 자연을 느끼고 싶은 분들한테 추천드려요 \uD83C\uDF33\n" +
//                    "        \n\n용머리해안 코스 / 산방굴사 코스가 나뉘어져 있으니 미리 검색해보고 가시면 좋아요. 각각 용머리해안이 보이는 경치 코스와 산방굴사가 있는 코스인데 유료입장이에요. 근데 날씨 영향을 많이 받아서 아쉬워요\uD83D\uDE2D 저희가 갔을때는 날이 흐려서 산방산에서 용머리 해안 경치가 잘 안보여서 산방굴사 코스를 선택할 수 밖에 없었어요 ,, 그리고 산방산 구경 후 용머리해안에 가보고 싶었는데 날씨 문제로 입장이 제한됐어요 .\n" +
//                    "        \n\n그래서 산방산에 가고싶은 분들은 여행일정 중 맑은날에 가시는걸 추천드려요 ! 마음처럼 되지는 않겠지만 오늘 날씨 진짜 좋은데 ? 싶으면 다음날 일정이랑 체인지를 해서라도 맑은날에 방문하시는게 베스트 일듯 해요",
//                null, null, null, false, true)
//        )

        contentsAdapter.setItems(spotList)
        //dailyContents[4].isLast = true
        binding.rvDailyContents.adapter = contentsAdapter

        if (spotList.size > 5) {
            routeAdapter.setItems(spotList.subList(0, 5))
            initMoreBtn(spotList)
            initCloseBtn(spotList)
        }
        else {
            binding.clLookMore.isVisible = false
            binding.clLookClose.isVisible = false
        }

        binding.rvDailyRoute.adapter = routeAdapter
    }

    private fun initMoreBtn(items: List<Spot>) {
        binding.clLookMore.setOnClickListener {
            //items[4].isLast = false
            routeAdapter.setItems(items)
            binding.clLookMore.isVisible = false
            binding.clLookClose.isVisible = true
        }
    }

    private fun initCloseBtn(items: List<Spot>) {
        binding.clLookClose.setOnClickListener {
            //items[4].isLast = true
            routeAdapter.setItems(items.subList(0, 5))
            binding.clLookMore.isVisible = true
            binding.clLookClose.isVisible = false
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}