package co.kr.bemyplan.ui.main.home

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import co.kr.bemyplan.databinding.ItemHomePlanBinding
import co.kr.bemyplan.domain.model.main.home.HomeDomainData
import co.kr.bemyplan.util.clipTo

class HomeAdapter(
    val beforePurchase: (Int, Boolean) -> Unit,
    val afterPurchase: (Int) -> Unit) : RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {

    val planList = mutableListOf<HomeDomainData>()

    inner class HomeViewHolder(private val binding: ItemHomePlanBinding):RecyclerView.ViewHolder(binding.root){
        fun onBind(data: HomeDomainData){
            binding.homeItem = data
            clipTo(binding.ivHomeCommon, data.thumbnailUrl)
            clickItem(data)
        }

        private fun clickItem(data: HomeDomainData) {
            binding.root.setOnClickListener {
                if (data.orderStatus == true) {
                    afterPurchase(data.planId)
                } else {
                    beforePurchase(data.planId, data.scrapStatus)
                }
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

    override fun getItemCount(): Int {
        Log.d("yongminAdapter", "홈 뷰 일정 개수${planList.size}")
        return planList.size
    }
}