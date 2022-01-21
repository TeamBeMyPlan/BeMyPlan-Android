package co.kr.bemyplan.ui.main.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import co.kr.bemyplan.data.entity.main.home.ResponseHomeData
import co.kr.bemyplan.databinding.ItemHomePlanBinding
import co.kr.bemyplan.util.clipTo

class HomeAdapter(
    val beforePurchase: (Int, Boolean) -> Unit,
    val afterPurchase: (Int) -> Unit) : RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {

    val planList = mutableListOf<ResponseHomeData.ResponseHomeItems.HomeData>()

    inner class HomeViewHolder(private val binding: ItemHomePlanBinding):RecyclerView.ViewHolder(binding.root){
        fun onBind(data: ResponseHomeData.ResponseHomeItems.HomeData){
            binding.homeItem = data
            clipTo(binding.ivHomeCommon, data.thumbnailUrl)
            clickItem(data)
        }

        private fun clickItem(data: ResponseHomeData.ResponseHomeItems.HomeData) {
            binding.root.setOnClickListener {
                if (data.isPurchased == true) {
                    afterPurchase(data.postId)
                } else {
                    beforePurchase(data.postId, data.isScraped)
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

    override fun getItemCount(): Int =planList.size
}