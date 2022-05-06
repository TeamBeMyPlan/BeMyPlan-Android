package co.kr.bemyplan.ui.main.scrap.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import co.kr.bemyplan.R
import co.kr.bemyplan.domain.model.list.ContentModel
import co.kr.bemyplan.databinding.ItemScrapEmptyContentBinding

class ScrapRecommendAdapter(private val itemClick: (ContentModel) -> Unit) :
    RecyclerView.Adapter<ScrapRecommendAdapter.ScrapRecommendViewHolder>() {
    private val asyncDiffer = AsyncListDiffer(this, diffCallback)

    class ScrapRecommendViewHolder(
        val binding: ItemScrapEmptyContentBinding,
        private val itemClick: (ContentModel) -> Unit
    ) :
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
        return ScrapRecommendViewHolder(binding, itemClick)
    }

    override fun onBindViewHolder(holder: ScrapRecommendViewHolder, position: Int) {
        holder.bind(asyncDiffer.currentList[position])
    }

    override fun getItemCount(): Int {
        return asyncDiffer.currentList.size
    }

    fun replaceItem(itemList: List<ContentModel>) {
        asyncDiffer.submitList(itemList)
    }

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<ContentModel>() {
            override fun areItemsTheSame(oldItem: ContentModel, newItem: ContentModel): Boolean {
                return oldItem.planId == newItem.planId
            }

            override fun areContentsTheSame(oldItem: ContentModel, newItem: ContentModel): Boolean {
                return oldItem == newItem
            }
        }
    }
}