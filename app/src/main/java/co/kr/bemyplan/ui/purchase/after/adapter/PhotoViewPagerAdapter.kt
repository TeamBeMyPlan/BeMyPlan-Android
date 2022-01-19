package co.kr.bemyplan.ui.purchase.after.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import co.kr.bemyplan.R
import co.kr.bemyplan.data.entity.main.home.ResponseHomePopularData
import co.kr.bemyplan.data.entity.purchase.after.Spot
import co.kr.bemyplan.databinding.ItemAfterPurchasePhotoBinding
import co.kr.bemyplan.databinding.ItemPopularBinding
import co.kr.bemyplan.util.clipTo

class PhotoViewPagerAdapter: RecyclerView.Adapter<PhotoViewPagerAdapter.PagerViewHolder>() {
    private var photoList = listOf<String>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PhotoViewPagerAdapter.PagerViewHolder {
        val binding: ItemAfterPurchasePhotoBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_after_purchase_photo, parent, false)
        return PagerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        holder.onBind(photoList[position])
    }

    override fun getItemCount() = photoList.size

    @SuppressLint("NotifyDataSetChanged")
    fun setItems(items: List<String>) {
        photoList = items
        notifyDataSetChanged()
    }

    inner class PagerViewHolder(private val binding: ItemAfterPurchasePhotoBinding):RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: String) {
            binding.photo = data
            clipTo(binding.ivPhoto, data)
        }
    }
}