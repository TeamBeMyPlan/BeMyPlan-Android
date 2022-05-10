package co.kr.bemyplan.ui.main.location

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import co.kr.bemyplan.databinding.ItemLocationBinding
import co.kr.bemyplan.domain.model.main.location.LocationData
import co.kr.bemyplan.util.ToastMessage.shortToast
import co.kr.bemyplan.util.clipTo

class LocationAdapter(val itemClick:(LocationData)->Unit, val myContext : Context) :
    ListAdapter<LocationData, LocationAdapter.LocationViewHolder>(LocationComparator()){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding=ItemLocationBinding.inflate(layoutInflater, parent, false)
        return LocationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LocationViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    inner class LocationViewHolder(private val binding: ItemLocationBinding):RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: LocationData) {
            binding.locationItem = data
            clipTo(binding.ivLocation, data.thumbnailUrl)
            binding.root.setOnClickListener {
                if (binding.ivLock.visibility == View.GONE) {
                    itemClick(data)
                } else {
                    myContext.shortToast("추후에 오픈될 예정입니다")
                }
            }
        }
    }

    private class LocationComparator:DiffUtil.ItemCallback<LocationData>(){
        override fun areItemsTheSame(oldItem: LocationData, newItem: LocationData): Boolean {
            return oldItem.region==newItem.region
        }

        override fun areContentsTheSame(oldItem: LocationData, newItem: LocationData): Boolean {
            return oldItem==newItem
        }

    }
}