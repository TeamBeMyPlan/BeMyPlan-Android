package co.kr.bemyplan.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import co.kr.bemyplan.data.TempHomeData
import co.kr.bemyplan.databinding.ItemHomePlanBinding
import com.bumptech.glide.Glide

class HomeAdapter : RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {
    val planList = mutableListOf<TempHomeData>()

    class HomeViewHolder(private val binding: ItemHomePlanBinding):RecyclerView.ViewHolder(binding.root){
        fun onBind(data:TempHomeData){
            binding.tvHomeCommon.text = data.text
            Glide.with(binding.ivHomeCommon.context).load(data.img).into(binding.ivHomeCommon)
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