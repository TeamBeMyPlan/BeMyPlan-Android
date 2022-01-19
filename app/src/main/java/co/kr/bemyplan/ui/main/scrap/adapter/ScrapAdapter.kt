package co.kr.bemyplan.ui.main.scrap.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import co.kr.bemyplan.R
import co.kr.bemyplan.data.entity.list.ContentModel
import co.kr.bemyplan.databinding.ItemScrapContentBinding

class ScrapAdapter(val itemClick: (ContentModel) -> Unit) :
    RecyclerView.Adapter<ScrapAdapter.ScrapViewHolder>() {
    var itemList: List<ContentModel> = listOf()

    inner class ScrapViewHolder(val binding: ItemScrapContentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(contentModel: ContentModel) {
            binding.model = contentModel
            binding.ivPhoto.clipToOutline = true
            binding.executePendingBindings()

            clickItem(contentModel)
//            clickScrap()
        }

        private fun clickItem(contentModel: ContentModel) {
            binding.root.setOnClickListener {
                itemClick(contentModel)
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
        return ScrapViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ScrapViewHolder, position: Int) {
        holder.bind(itemList[position])
    }

    override fun getItemCount(): Int {
        return itemList.size
    }
}