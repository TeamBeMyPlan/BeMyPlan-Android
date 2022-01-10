package co.kr.bemyplan.ui.purchase.before.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import co.kr.bemyplan.R
import co.kr.bemyplan.data.purchase.before.ContentModel
import co.kr.bemyplan.databinding.ItemContentBinding

class ContentAdapter: RecyclerView.Adapter<ContentAdapter.ContentViewHolder>() {
    var itemList: List<ContentModel> = listOf()

    class ContentViewHolder(private val binding: ItemContentBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(contentModel: ContentModel) {
            binding.model = contentModel
            binding.ivPhoto.clipToOutline = true
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContentViewHolder {
        val binding = DataBindingUtil.inflate<ItemContentBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_content,
            parent,
            false
        )
        return ContentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ContentViewHolder, position: Int) {
        holder.bind(itemList[position])
    }

    override fun getItemCount(): Int {
        return itemList.size
    }
}