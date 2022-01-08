package co.kr.bemyplan.ui.purchase.after.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import co.kr.bemyplan.data.DailyContents
import co.kr.bemyplan.databinding.DailyContentsListBinding

class DailyContentsAdapter: RecyclerView.Adapter<DailyContentsAdapter.DailyContentsViewHolder>() {

    private val dailyContentsList = mutableListOf<DailyContents>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyContentsViewHolder {
        val binding = DailyContentsListBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )

        return DailyContentsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DailyContentsViewHolder, position: Int) {
        holder.onBind(dailyContentsList[position])
    }

    override fun getItemCount() = dailyContentsList.size

    class DailyContentsViewHolder(private val binding: DailyContentsListBinding)
        : RecyclerView.ViewHolder(binding.root) {
            fun onBind(data: DailyContents) {
                binding.tvPlace.text = data.placeName
                binding.tvContext.text = data.context
                //TODO: 이미지 넣어야 함
            }
        }
}