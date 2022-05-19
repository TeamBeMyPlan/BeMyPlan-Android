package co.kr.bemyplan.ui.main.scrap.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import co.kr.bemyplan.R
import co.kr.bemyplan.domain.model.list.ContentModel
import co.kr.bemyplan.databinding.ItemScrapContentBinding

class ScrapAdapter(private val itemClick: (ContentModel) -> Unit, private val scrapClick: (Int, Boolean) -> Unit) :
    RecyclerView.Adapter<ScrapAdapter.ScrapViewHolder>() {
    private val asyncDiffer = AsyncListDiffer(this, diffCallback)

    class ScrapViewHolder(
        private val binding: ItemScrapContentBinding,
        private val itemClick: (ContentModel) -> Unit,
        private val scrapClick: (Int, Boolean) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(contentModel: ContentModel) {
            contentModel.scrapStatus = true
            binding.model = contentModel
            binding.ivPhoto.clipToOutline = true
            binding.executePendingBindings()

            clickItem(contentModel)
            clickScrap(contentModel)
        }

        private fun reDrawView(contentModel: ContentModel) {
            binding.model = contentModel
        }

        private fun clickItem(contentModel: ContentModel) {
            binding.root.setOnClickListener {
                itemClick(contentModel)
            }
        }

        private fun clickScrap(contentModel: ContentModel) {
            binding.layoutScrap.setOnClickListener {
                scrapClick(contentModel.planId, contentModel.scrapStatus)
                contentModel.scrapStatus = !contentModel.scrapStatus
                reDrawView(contentModel)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScrapViewHolder {
        val binding = DataBindingUtil.inflate<ItemScrapContentBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_scrap_content,
            parent,
            false
        )
        return ScrapViewHolder(binding, itemClick, scrapClick)
    }

    override fun onBindViewHolder(holder: ScrapViewHolder, position: Int) {
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