package co.kr.bemyplan.ui.main.location

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import co.kr.bemyplan.R
import co.kr.bemyplan.databinding.FragmentLocationBinding
import co.kr.bemyplan.ui.list.ListActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LocationFragment : Fragment() {

    private var _binding: FragmentLocationBinding? = null
    private val binding get() = _binding ?: error("Binding이 초기화 되지 않았습니다.")
    private lateinit var locationAdapter: LocationAdapter
    private val locationViewModel: LocationViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLocationBinding.inflate(layoutInflater)
        locationViewModel.getLocationData()
        initAdapter()
        locationViewModel.locationData.observe(viewLifecycleOwner) {
            locationAdapter.submitList(it)
        }
        return binding.root
    }

    private fun initAdapter() {
        locationAdapter = LocationAdapter(itemClick = {
            val intent = Intent(requireContext(), ListActivity::class.java)
            intent.putExtra("from", "location")
            intent.putExtra("region", it.region)
            intent.putExtra("locationName", it.name)
            startActivity(intent)
        }, myContext = requireContext())
        binding.rvLocation.adapter = locationAdapter
        binding.rvLocation.addItemDecoration(
            VerticalItemDecorator(
                resources.getDimensionPixelOffset(
                    R.dimen.margin_12
                )
            )
        )
        binding.rvLocation.addItemDecoration(
            HorizontalItemDecorator(
                resources.getDimensionPixelOffset(
                    R.dimen.margin_6
                )
            )
        )
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}