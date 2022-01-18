package co.kr.bemyplan.ui.main.location

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import co.kr.bemyplan.data.entity.main.location.LocationData
import co.kr.bemyplan.databinding.FragmentLocationBinding
import co.kr.bemyplan.databinding.ItemLocationBinding
import co.kr.bemyplan.util.clipTo
import com.bumptech.glide.Glide

class LocationAdapter(val itemClick: (LocationData) -> Unit) :
    RecyclerView.Adapter<LocationAdapter.LocationViewHolder>() {

    val locationList = mutableListOf<LocationData>()

    private lateinit var binding: FragmentLocationBinding
    private var last: Boolean = false

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): LocationAdapter.LocationViewHolder {
        val binding =
            ItemLocationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LocationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LocationAdapter.LocationViewHolder, position: Int) {
        holder.onBind(locationList[position])
    }

    override fun getItemCount(): Int {
        return locationList.size
    }

    inner class LocationViewHolder(private val binding: ItemLocationBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: LocationData) {
            binding.tvLocation.text = data.text
            Glide.with(binding.ivLocation.context).load(data.img).into(binding.ivLocation)
            //clipTo(binding.ivLocation, data.img)
            if (data.lock == true) {
                binding.ivLock.visibility = View.VISIBLE
            }

            binding.root.setOnClickListener {
                itemClick(data)
            }
        }
    }
}