package co.kr.bemyplan.ui.main.scrap.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import co.kr.bemyplan.R
import co.kr.bemyplan.data.entity.list.ContentModel
import co.kr.bemyplan.databinding.ItemScrapEmptyContentBinding

class ScrapRecommendAdapter(val context: Context, val itemClick: (ContentModel) -> Unit) :
    RecyclerView.Adapter<ScrapRecommendAdapter.ScrapRecommendViewHolder>() {
    var itemList: List<ContentModel> = listOf()

    inner class ScrapRecommendViewHolder(val binding: ItemScrapEmptyContentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(contentModel: ContentModel) {
            binding.model = contentModel
            binding.ivPhoto.clipToOutline = true
            binding.executePendingBindings()

            binding.root.setOnClickListener {
                itemClick(contentModel)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScrapRecommendViewHolder {
        val binding = DataBindingUtil.inflate<ItemScrapEmptyContentBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_scrap_empty_content,
            parent,
            false
        )
        return ScrapRecommendViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ScrapRecommendViewHolder, position: Int) {
        holder.bind(itemList[position])
    }

    override fun getItemCount(): Int {
        return itemList.size
    }
}