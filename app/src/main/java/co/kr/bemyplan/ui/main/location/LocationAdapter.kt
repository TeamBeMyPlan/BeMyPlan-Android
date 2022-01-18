package co.kr.bemyplan.ui.main.location

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import co.kr.bemyplan.data.entity.main.location.LocationData
import co.kr.bemyplan.data.entity.main.location.ResponseLocationData
import co.kr.bemyplan.databinding.FragmentLocationBinding
import co.kr.bemyplan.databinding.ItemLocationBinding
import co.kr.bemyplan.util.clipTo
import com.bumptech.glide.Glide

class LocationAdapter : RecyclerView.Adapter<LocationAdapter.LocationViewHolder>() {

    val locationList = mutableListOf<ResponseLocationData.LocationData>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): LocationAdapter.LocationViewHolder {
        val binding = ItemLocationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LocationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LocationAdapter.LocationViewHolder, position: Int) {
        holder.onBind(locationList[position])
    }

    override fun getItemCount(): Int {
        return locationList.size
    }

    class LocationViewHolder(private val binding:ItemLocationBinding):RecyclerView.ViewHolder(binding.root){
        fun onBind(data: ResponseLocationData.LocationData){

            binding.locationItem=data
            clipTo(binding.ivLocation, data.photo_url)

            if(data.is_activated){
                binding.ivLock.visibility=View.VISIBLE
            }else{binding.ivLock.visibility=View.GONE}
        }
    }
}