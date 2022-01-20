package co.kr.bemyplan.ui.main.location

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import co.kr.bemyplan.R
import co.kr.bemyplan.databinding.FragmentLocationBinding
import co.kr.bemyplan.ui.list.ListActivity

class LocationFragment : Fragment() {

    private var _binding : FragmentLocationBinding? = null
    private val binding get() = _binding?:error("Binding이 초기화 되지 않았습니다.")
    private lateinit var locationAdapter: LocationAdapter
    private val lacationViewModel: LocationViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLocationBinding.inflate(layoutInflater)
        lacationViewModel.initLocationNetwork()
        initAdapter()

        return binding.root
    }

    private fun initAdapter(){
        locationAdapter = LocationAdapter {
            val intent = Intent(requireContext(), ListActivity::class.java)
            intent.putExtra("from", "location")
            startActivity(intent)
        }
        binding.rvLocation.adapter=locationAdapter
        binding.rvLocation.addItemDecoration(VerticalItemDecorator(resources.getDimensionPixelOffset(R.dimen.margin_12)))
        binding.rvLocation.addItemDecoration(HorizontalItemDecorator(resources.getDimensionPixelOffset(R.dimen.margin_6)))

        lacationViewModel.location.observe(viewLifecycleOwner){
            locationAdapter.locationList.addAll(it)
            locationAdapter.notifyDataSetChanged()
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}