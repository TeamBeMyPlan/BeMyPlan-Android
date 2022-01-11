package co.kr.bemyplan.ui.main.location

import android.graphics.Rect
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.kr.bemyplan.R
import co.kr.bemyplan.data.location.LocationData
import co.kr.bemyplan.databinding.FragmentHomeBinding
import co.kr.bemyplan.databinding.FragmentLocationBinding
import co.kr.bemyplan.databinding.ItemLocationBinding

class LocationFragment : Fragment() {

    private var _binding : FragmentLocationBinding? = null
    private val binding get() = _binding?:error("Binding이 초기화 되지 않았습니다.")
    private lateinit var locationAdapter: LocationAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLocationBinding.inflate(layoutInflater)
        initAdapter()

        return binding.root
    }

    private fun initAdapter(){
        //val itemDecoration = LocationAdapter.SpaceItemDecoration(12, 6)
        locationAdapter = LocationAdapter()
        binding.rvLocation.adapter=locationAdapter
        binding.rvLocation.addItemDecoration(VerticalItemDecorator(12))
        binding.rvLocation.addItemDecoration(HorizontalItemDecorator(6))
        locationAdapter.locationList.addAll(
            listOf(
                LocationData("부산", R.drawable.img_busan, false),
                LocationData("부산", R.drawable.img_busan, false),
                LocationData("부산", R.drawable.img_busan, false),
                LocationData("강릉", R.drawable.img_gangneung, true),
                LocationData("강릉", R.drawable.img_gangneung, true),
                LocationData("강릉", R.drawable.img_gangneung, true),
                LocationData("강릉", R.drawable.img_gangneung, true),
                LocationData("강릉", R.drawable.img_gangneung, true)
            )
        )
    }
}