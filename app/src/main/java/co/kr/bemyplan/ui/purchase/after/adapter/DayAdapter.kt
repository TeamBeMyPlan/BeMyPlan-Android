package co.kr.bemyplan.ui.purchase.after.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import co.kr.bemyplan.R
import co.kr.bemyplan.data.DailyContents
import co.kr.bemyplan.databinding.ItemDayButtonBinding

class DayAdapter: RecyclerView.Adapter<DayAdapter.DayViewHolder>() {

    var dailyContentsList = listOf<DailyContents>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayViewHolder {
        val binding = ItemDayButtonBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )

        return DayViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DayViewHolder, position: Int) {
        holder.onBind(dailyContentsList[position])
    }

    override fun getItemCount() = dailyContentsList.size

    @SuppressLint("NotifyDataSetChanged")
    fun setItems(items: List<DailyContents>) {
        dailyContentsList = items
        notifyDataSetChanged()
    }

    class DayViewHolder(private val binding: ItemDayButtonBinding): RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: DailyContents) {
            //binding.dailyContents = data
            binding.tvDayButton.setOnClickListener {
                it.isSelected = true
            }
        }
    }
}