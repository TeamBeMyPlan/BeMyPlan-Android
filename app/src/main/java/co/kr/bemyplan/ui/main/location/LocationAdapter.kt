package co.kr.bemyplan.ui.main.location

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import co.kr.bemyplan.R
import co.kr.bemyplan.data.entity.main.location.ResponseLocationData
import co.kr.bemyplan.databinding.ItemLocationBinding
import co.kr.bemyplan.util.ToastMessage
import co.kr.bemyplan.util.ToastMessage.shortToast
import co.kr.bemyplan.util.clipTo
import java.util.zip.GZIPOutputStream

class LocationAdapter(val itemClick: (ResponseLocationData.LocationData) -> Unit, val myContext : Context) :
    RecyclerView.Adapter<LocationAdapter.LocationViewHolder>() {

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
        holder.itemView.setOnClickListener{

        }
    }

    override fun getItemCount(): Int {
        return locationList.size
    }

    inner class LocationViewHolder(private val binding:ItemLocationBinding):RecyclerView.ViewHolder(binding.root){
        fun onBind(data: ResponseLocationData.LocationData){

            binding.locationItem=data
            clipTo(binding.ivLocation, data.photoUrl)
            binding.root.setOnClickListener{
                if(binding.ivLock.visibility== View.GONE) { itemClick(data) }
                else { myContext.shortToast("추후에 오픈될 예정입니다") }
            }
        }
    }
}