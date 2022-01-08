package co.kr.bemyplan.ui.purchase.before.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import co.kr.bemyplan.R
import co.kr.bemyplan.data.purchase.before.OverviewModel
import co.kr.bemyplan.databinding.ItemOverviewBinding

class OverviewAdapter : RecyclerView.Adapter<OverviewAdapter.OverviewViewHolder>() {
    var itemList: List<OverviewModel> = listOf()

    class OverviewViewHolder(private val binding: ItemOverviewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(overviewModel: OverviewModel) {
            binding.model = overviewModel
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OverviewViewHolder {
        val binding = DataBindingUtil.inflate<ItemOverviewBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_overview,
            parent,
            false
        )
        return OverviewViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OverviewViewHolder, position: Int) {
        holder.bind(itemList[position])
    }

    override fun getItemCount(): Int {
        return itemList.size
    }
}