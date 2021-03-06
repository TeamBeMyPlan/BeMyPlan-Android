package co.kr.bemyplan.ui.purchase.before.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import co.kr.bemyplan.R
import co.kr.bemyplan.databinding.ItemBeforePurchaseContentBinding
import co.kr.bemyplan.domain.model.purchase.before.PreviewContent
import co.kr.bemyplan.domain.model.purchase.before.PreviewContents

class ContentAdapter : RecyclerView.Adapter<ContentAdapter.ContentViewHolder>() {
    private val asyncDiffer = AsyncListDiffer(this, diffCallback)

    class ContentViewHolder(private val binding: ItemBeforePurchaseContentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(content: PreviewContent) {
            binding.content = content
            binding.ivPhoto.clipToOutline = true
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContentViewHolder {
        val binding = DataBindingUtil.inflate<ItemBeforePurchaseContentBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_before_purchase_content,
            parent,
            false
        )
        return ContentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ContentViewHolder, position: Int) {
        holder.bind(asyncDiffer.currentList[position])
    }

    override fun getItemCount(): Int {
        return asyncDiffer.currentList.size
    }

    fun replaceItem(itemList: List<PreviewContent>) {
        asyncDiffer.submitList(itemList)
    }

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<PreviewContent>() {
            override fun areItemsTheSame(
                oldItem: PreviewContent,
                newItem: PreviewContent
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: PreviewContent,
                newItem: PreviewContent
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}