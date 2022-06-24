package co.kr.bemyplan.ui.main.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import co.kr.bemyplan.databinding.ItemHomePlanBinding
import co.kr.bemyplan.domain.model.main.home.HomeDomainData
import co.kr.bemyplan.util.clipTo

class HomeAdapter(private val itemClick: (HomeDomainData) -> Unit) :
    RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {

    val planList = mutableListOf<HomeDomainData>()

    class HomeViewHolder(
        private val binding: ItemHomePlanBinding,
        private val itemClick: (HomeDomainData) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: HomeDomainData) {
            binding.homeItem = data
            clipTo(binding.ivHomeCommon, data.thumbnailUrl)
            clickItem(data)
        }

        private fun clickItem(data: HomeDomainData) {
            binding.root.setOnClickListener {
                itemClick(data)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val binding =
            ItemHomePlanBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeViewHolder(binding, itemClick)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        holder.onBind(planList[position])
    }

    override fun getItemCount(): Int {
        return planList.size
    }
}