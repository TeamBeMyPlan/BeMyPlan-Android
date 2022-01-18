package co.kr.bemyplan.ui.main.location

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import co.kr.bemyplan.R
import co.kr.bemyplan.data.entity.main.location.ResponseLocationData
import co.kr.bemyplan.databinding.ItemLocationBinding
import co.kr.bemyplan.util.clipTo

class LocationAdapter : RecyclerView.Adapter<LocationAdapter.LocationViewHolder>() {

    val locationList = mutableListOf<ResponseLocationData.LocationData>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): LocationAdapter.LocationViewHolder {
        val binding : ItemLocationBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_location, parent, false)
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
        }
    }
}