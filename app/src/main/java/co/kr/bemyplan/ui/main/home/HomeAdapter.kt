package co.kr.bemyplan.ui.main.home

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import co.kr.bemyplan.data.entity.main.home.TempHomeData
import co.kr.bemyplan.databinding.ItemHomePlanBinding
import co.kr.bemyplan.util.clipTo
import com.bumptech.glide.Glide

class HomeAdapter(val itemClick: (TempHomeData) -> Unit) : RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {
    val planList = mutableListOf<TempHomeData>()

    inner class HomeViewHolder(private val binding: ItemHomePlanBinding):RecyclerView.ViewHolder(binding.root){
        fun onBind(data: TempHomeData){
            Log.d("bindtest", data.text)
            binding.tvHomeCommon.text = data.text
            Glide.with(binding.ivHomeCommon.context).load(data.img).into(binding.ivHomeCommon)
            //clipTo(binding.ivHomeCommon, data.img)
            clickItem(data)
        }

        private fun clickItem(data: TempHomeData) {
            binding.root.setOnClickListener {
                itemClick(data)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val binding = ItemHomePlanBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        holder.onBind(planList[position])
    }

    override fun getItemCount(): Int =planList.size
}