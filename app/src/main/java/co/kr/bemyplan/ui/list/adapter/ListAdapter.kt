package co.kr.bemyplan.ui.list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import co.kr.bemyplan.R
import co.kr.bemyplan.data.list.ContentModel
import co.kr.bemyplan.databinding.ItemListContentBinding

class ListAdapter: RecyclerView.Adapter<ListAdapter.ListViewHolder>() {
    var itemList: List<ContentModel> = listOf()

    class ListViewHolder(private val binding: ItemListContentBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(contentModel: ContentModel) {
            binding.model = contentModel
            binding.ivPhoto.clipToOutline = true
            binding.executePendingBindings()

            clickScrap()
        }

        private fun clickScrap() {
            binding.ivScrap.setOnClickListener {
                binding.ivScrap.apply {
                    isSelected = !isSelected
                }
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
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(itemList[position])
    }

    override fun getItemCount(): Int {
        return itemList.size
    }
}