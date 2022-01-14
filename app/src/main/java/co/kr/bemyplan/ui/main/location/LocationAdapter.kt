package co.kr.bemyplan.ui.main.location

import android.content.Context
import android.graphics.Rect
import android.location.Location
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.marginBottom
import androidx.recyclerview.widget.RecyclerView
import co.kr.bemyplan.R
import co.kr.bemyplan.data.home.TempHomeData
import co.kr.bemyplan.data.location.LocationData
import co.kr.bemyplan.databinding.FragmentLocationBinding
import co.kr.bemyplan.databinding.ItemLocationBinding
import com.bumptech.glide.Glide

class LocationAdapter : RecyclerView.Adapter<LocationAdapter.LocationViewHolder>() {

    val locationList = mutableListOf<LocationData>()

    private lateinit var binding : FragmentLocationBinding
    private var last : Boolean = false

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
        fun onBind(data:LocationData){
            binding.tvLocation.text=data.text
            binding.ivLock.clipToOutline = true
            binding.ivLocation.clipToOutline = true
            Glide.with(binding.ivLocation.context).load(data.img).into(binding.ivLocation)
            if(data.lock==true){
                binding.ivLock.visibility=View.VISIBLE
            }
        }
    }
}