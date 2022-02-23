package co.kr.bemyplan.ui.list.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import co.kr.bemyplan.R
import co.kr.bemyplan.data.entity.list.ContentModel
import co.kr.bemyplan.databinding.ItemListContentBinding

class ListAdapter(
    private val itemClick: (ContentModel) -> Unit,
    private val scrapClick: (Int) -> Unit
) :
    RecyclerView.Adapter<ListAdapter.ListViewHolder>() {
    var itemList: List<ContentModel> = listOf()

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

            Log.d(
                "mlog: price & itemId",
                contentModel.price.toString() + "," + contentModel.postId.toString()
            )

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
                scrapClick(contentModel.postId)
                contentModel.isScraped = !contentModel.isScraped
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
        holder.bind(itemList[position])
    }

    override fun getItemCount(): Int {
        return itemList.size
    }
}