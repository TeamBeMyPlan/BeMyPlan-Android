package co.kr.bemyplan.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import co.kr.bemyplan.data.TempHomeData
import co.kr.bemyplan.databinding.ItemPopularBinding
import com.bumptech.glide.Glide

class HomeViewPagerAdapter :
    RecyclerView.Adapter<HomeViewPagerAdapter.PagerViewHolder>() {

    val planList = mutableListOf<TempHomeData>()

    class PagerViewHolder(private val binding:ItemPopularBinding):RecyclerView.ViewHolder(binding.root){
        fun onBind(data:TempHomeData){
            binding.tvPopular1.text = data.text
            binding.tvPopular2.text = data.text2
            Glide.with(binding.ivPopular.context).load(data.img).into(binding.ivPopular)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerViewHolder {
        val binding = ItemPopularBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PagerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        holder.onBind(planList[position])
    }

    override fun getItemCount(): Int = planList.size

}