package co.kr.bemyplan.ui.list.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import co.kr.bemyplan.R
import co.kr.bemyplan.domain.model.list.ContentModel
import co.kr.bemyplan.databinding.ItemListContentBinding
import timber.log.Timber

class ListAdapter(
    private val itemClick: (ContentModel) -> Unit,
    private val scrapClick: (Int) -> Unit
) :
    RecyclerView.Adapter<ListAdapter.ListViewHolder>() {
    private val asyncDiffer = AsyncListDiffer(this, diffCallback)

    class ListViewHolder(
        private val binding: ItemListContentBinding,
        private val itemClick: (ContentModel) -> Unit,
        private val scrapClick: (Int) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(contentModel: ContentModel) {
            binding.model = contentModel
            binding.ivPhoto.clipToOutline = true
            binding.executePendingBindings()

            Timber.d(contentModel.price.toString() + "," + contentModel.planId.toString())

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
                scrapClick(contentModel.planId)
                contentModel.scrapStatus = !contentModel.scrapStatus
                reDrawView(contentModel)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = DataBindingUtil.inflate<ItemListContentBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_list_content,
            parent,
            false
        )
        return ListViewHolder(binding, itemClick, scrapClick)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
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