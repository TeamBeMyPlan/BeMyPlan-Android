package co.kr.bemyplan.ui.purchase.after.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.RoundedCorner
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableArrayList
import androidx.recyclerview.widget.RecyclerView
import co.kr.bemyplan.data.DailyContents
import co.kr.bemyplan.data.Spot
import co.kr.bemyplan.databinding.ItemDailyContentsBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions

class DailyContentsAdapter: RecyclerView.Adapter<DailyContentsAdapter.DailyContentsViewHolder>() {

    private val dailyContentsList = mutableListOf<DailyContents>()
    var spotList = listOf<Spot>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyContentsViewHolder {
        Log.d("hellll","initAdapter")
        val binding = ItemDailyContentsBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )

        return DailyContentsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DailyContentsViewHolder, position: Int) {
        holder.onBind(spotList[position])
    }

    override fun getItemCount() = spotList.size

    class DailyContentsViewHolder(private val binding: ItemDailyContentsBinding)
        : RecyclerView.ViewHolder(binding.root) {
            fun onBind(data: Spot) {

                binding.spot = data
                Glide.with(binding.root)
                    .load(data.photo)
                    .apply(RequestOptions.bitmapTransform(RoundedCorners(5)))
                    .into(binding.ivPhoto)
            }
        }

    fun setItems(items: List<Spot>) {
        Log.d("asdf","asdfasdf")
        spotList = items
        notifyDataSetChanged()
    }

    @BindingAdapter("bind:spotList")
    fun bindSpotList(recyclerView: RecyclerView, items: ObservableArrayList<Spot>) {
        val adapter = recyclerView.adapter as? DailyContentsAdapter
        adapter?.setItems(items)
    }
}