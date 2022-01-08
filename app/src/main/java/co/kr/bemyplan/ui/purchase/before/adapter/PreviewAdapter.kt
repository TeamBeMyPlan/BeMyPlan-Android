package co.kr.bemyplan.ui.purchase.before.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import co.kr.bemyplan.R
import co.kr.bemyplan.data.purchase.before.PreviewModel
import co.kr.bemyplan.databinding.ItemPreviewBinding

class PreviewAdapter: RecyclerView.Adapter<PreviewAdapter.PreviewViewHolder>() {
    var itemList: List<PreviewModel> = listOf()

    class PreviewViewHolder(private val binding: ItemPreviewBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(previewModel: PreviewModel) {
            binding.model = previewModel
            binding.ivPhoto.clipToOutline = true
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PreviewViewHolder {
        val binding = DataBindingUtil.inflate<ItemPreviewBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_preview,
            parent,
            false
        )
        return PreviewViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PreviewViewHolder, position: Int) {
        holder.bind(itemList[position])
    }

    override fun getItemCount(): Int {
        return itemList.size
    }
}