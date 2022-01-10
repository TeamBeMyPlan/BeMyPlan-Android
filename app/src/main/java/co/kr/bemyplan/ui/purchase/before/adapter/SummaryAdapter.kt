package co.kr.bemyplan.ui.purchase.before.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import co.kr.bemyplan.R
import co.kr.bemyplan.data.purchase.before.SummaryModel
import co.kr.bemyplan.databinding.ItemSummaryBinding

class SummaryAdapter : RecyclerView.Adapter<SummaryAdapter.SummaryViewHolder>() {
    var itemList: List<SummaryModel> = listOf()

    class SummaryViewHolder(private val binding: ItemSummaryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(overviewModel: SummaryModel) {
            binding.model = overviewModel
            binding.executePendingBindings()

            if(adapterPosition%4 == 0) {
                binding.layoutLinear1.visibility = View.INVISIBLE
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SummaryViewHolder {
        val binding = DataBindingUtil.inflate<ItemSummaryBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_summary,
            parent,
            false
        )
        return SummaryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SummaryViewHolder, position: Int) {
        holder.bind(itemList[position])
    }

    override fun getItemCount(): Int {
        return itemList.size
    }
}