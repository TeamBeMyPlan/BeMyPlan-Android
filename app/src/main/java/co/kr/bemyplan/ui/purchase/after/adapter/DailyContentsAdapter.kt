package co.kr.bemyplan.ui.purchase.after.adapter

import android.view.LayoutInflater
import android.view.RoundedCorner
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import co.kr.bemyplan.data.DailyContents
import co.kr.bemyplan.data.Spot
import co.kr.bemyplan.databinding.DailyContentsListBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions

class DailyContentsAdapter: RecyclerView.Adapter<DailyContentsAdapter.DailyContentsViewHolder>() {

    private val dailyContentsList = mutableListOf<DailyContents>()
    val spotList = mutableListOf<Spot>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyContentsViewHolder {
        val binding = DailyContentsListBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )

        return DailyContentsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DailyContentsViewHolder, position: Int) {
        holder.onBind(spotList[position])
    }

    override fun getItemCount() = dailyContentsList.size

    class DailyContentsViewHolder(private val binding: DailyContentsListBinding)
        : RecyclerView.ViewHolder(binding.root) {
            fun onBind(data: Spot) {
                binding.tvPlace.text = data.placeName
                binding.tvContext.text = data.context
                binding.tvAddress.text = data.address
                binding.tvMovingTime.text = data.duration.toString()
                Glide.with(binding.root)
                    .load(data.photo)
                    .apply(RequestOptions.bitmapTransform(RoundedCorners(5)))
                    .into(binding.ivPhoto)
            }
        }
}