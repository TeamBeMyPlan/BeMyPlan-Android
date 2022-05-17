package co.kr.bemyplan.ui.main.myplan.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import co.kr.bemyplan.databinding.ItemMyPlanPurchaseListBinding
import co.kr.bemyplan.domain.model.list.ContentModel
import co.kr.bemyplan.ui.list.adapter.ListAdapter
import timber.log.Timber

class MyPlanAdapter(
    private val itemClick: (ContentModel) -> Unit,
    private val scrapClick: (Int) -> Unit
) :
    RecyclerView.Adapter<MyPlanAdapter.ExistMyPlanViewHolder>() {
    private val asyncDiffer = AsyncListDiffer(this, diffCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExistMyPlanViewHolder {
        val binding = ItemMyPlanPurchaseListBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )

        return ExistMyPlanViewHolder(binding, itemClick, scrapClick)
    }

    override fun onBindViewHolder(holder: ExistMyPlanViewHolder, position: Int) {
        holder.onBind(asyncDiffer.currentList[position])
    }

    override fun getItemCount() = asyncDiffer.currentList.size

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

    class ExistMyPlanViewHolder(
        private val binding: ItemMyPlanPurchaseListBinding,
        private val itemClick: (ContentModel) -> Unit,
        private val scrapClick: (Int) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(contentModel: ContentModel) {
            Timber.tag("mlog: MyPlanAdapter").d(contentModel.planId.toString())
            binding.model = contentModel
            binding.ivMyPlanSpot.clipToOutline = true
            clickItem(contentModel)
            clickScrap(contentModel)
        }

        private fun clickItem(data: ContentModel) {
            binding.root.setOnClickListener {
                itemClick(data)
            }
        }

        private fun clickScrap(contentModel: ContentModel) {
            binding.layoutScrap.setOnClickListener {
                scrapClick(contentModel.planId)
                contentModel.scrapStatus = !contentModel.scrapStatus
                reDrawView(contentModel)
            }
        }

        private fun reDrawView(contentModel: ContentModel) {
            binding.model = contentModel
        }
    }
}