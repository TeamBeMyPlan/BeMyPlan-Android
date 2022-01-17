package co.kr.bemyplan.ui.purchase.before.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import co.kr.bemyplan.R
import co.kr.bemyplan.data.entity.purchase.before.ContentModel
import co.kr.bemyplan.databinding.ItemBeforePurchaseContentBinding

class ContentViewPagerAdapter :
    RecyclerView.Adapter<ContentViewPagerAdapter.ContentViewPagerViewHolder>() {
    var itemList: List<ContentModel> = listOf()

    inner class ContentViewPagerViewHolder(val binding: ItemBeforePurchaseContentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(contentModel: ContentModel) {
            binding.model = contentModel
            binding.ivPhoto.clipToOutline = true
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContentViewPagerViewHolder {
        val binding = DataBindingUtil.inflate<ItemBeforePurchaseContentBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_before_purchase_content,
            parent,
            false
        )
        return ContentViewPagerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ContentViewPagerViewHolder, position: Int) {
        holder.bind(itemList[position])
    }

    override fun getItemCount(): Int {
        return itemList.size
    }
}