package co.kr.bemyplan.ui.purchase.after.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import co.kr.bemyplan.R
import co.kr.bemyplan.databinding.ItemAfterPurchasePhotoBinding
import co.kr.bemyplan.domain.model.purchase.after.Images
import co.kr.bemyplan.util.clipTo

class PhotoViewPagerAdapter(val photoUrl: ((String) -> Unit)?): RecyclerView.Adapter<PhotoViewPagerAdapter.PagerViewHolder>() {
    private var photoList = listOf<Images>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PagerViewHolder {
        val binding: ItemAfterPurchasePhotoBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_after_purchase_photo, parent, false)
        return PagerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        holder.onBind(photoList[position])
    }

    override fun getItemCount() = photoList.size

    @SuppressLint("NotifyDataSetChanged")
    fun setItems(items: List<Images>) {
        photoList = items
        notifyDataSetChanged()
    }

    inner class PagerViewHolder(private val binding: ItemAfterPurchasePhotoBinding):RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: Images) {
            binding.photo = data.url
            clipTo(binding.ivPhoto, data.url)

            binding.ivPhoto.setOnClickListener {
                photoUrl?.invoke(data.url)
            }
        }
    }
}