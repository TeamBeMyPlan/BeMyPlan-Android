package co.kr.bemyplan.ui.purchase.after.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableArrayList
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import co.kr.bemyplan.data.entity.Spot
import co.kr.bemyplan.databinding.ItemDailyContentsBinding
import co.kr.bemyplan.databinding.ItemDailyRouteBinding
import co.kr.bemyplan.util.clipTo
import java.lang.IllegalStateException

class DailyContentsAdapter(private val viewType: Int): RecyclerView.Adapter<DailyContentsAdapter.DailyContentsViewHolder>() {

    private var spotList = listOf<Spot>()

    override fun getItemViewType(position: Int) = viewType

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = when(viewType) {
        TYPE_CONTENTS -> {
            val binding = ItemDailyContentsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
            ContentsViewHolder(binding)
        }
        TYPE_ROUTE -> {
            val binding = ItemDailyRouteBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
            RouteViewHolder(binding)
        }
        else -> {
            throw IllegalStateException("Not Found ViewHolder Type $viewType")
        }
    }

    override fun onBindViewHolder(holder: DailyContentsViewHolder, position: Int) {
        when(holder) {
            is ContentsViewHolder -> {
                holder.onBind(spotList[position])
            }
            is RouteViewHolder -> {
                holder.onBind(spotList[position])
            }
        }
    }

    override fun getItemCount() = spotList.size

    @SuppressLint("NotifyDataSetChanged")
    fun setItems(items: List<Spot>) {
        spotList = items
        notifyDataSetChanged()
    }

    // MVVM을 위한 코드
   @BindingAdapter("bind:spotList")
    fun bindSpotList(recyclerView: RecyclerView, items: ObservableArrayList<Spot>) {
        val adapter = recyclerView.adapter as? DailyContentsAdapter
        adapter?.setItems(items)
    }

    abstract class DailyContentsViewHolder(private val binding: ViewDataBinding)
        : RecyclerView.ViewHolder(binding.root) {
        abstract fun onBind(data: Spot)
    }

    class ContentsViewHolder(private val binding: ItemDailyContentsBinding): DailyContentsViewHolder(binding) {
        override fun onBind(data: Spot) {
            binding.spot = data
            clipTo(binding.ivPhoto, data.photo)
        }
    }

    class RouteViewHolder(private val binding: ItemDailyRouteBinding): DailyContentsViewHolder(binding) {
        override fun onBind(data: Spot) {
            binding.spot = data
        }
    }

    companion object {
        const val TYPE_CONTENTS = 0
        const val TYPE_ROUTE = 1
    }
}