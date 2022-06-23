package co.kr.bemyplan.ui.main.myplan.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import co.kr.bemyplan.databinding.ItemMyPlanPurchaseListBinding
import co.kr.bemyplan.domain.model.main.myplan.MyPlanData

class MyPlanAdapter(
    private val itemClick: (MyPlanData.Data) -> Unit,
    private val scrapClick: (MyPlanData.Data) -> Unit
) :
    ListAdapter<MyPlanData.Data, MyPlanAdapter.ExistMyPlanViewHolder>(MyPlanComparator()) {

    class ExistMyPlanViewHolder(
        private val binding: ItemMyPlanPurchaseListBinding,
        private val itemClick: (MyPlanData.Data) -> Unit,
        private val scrapClick: (MyPlanData.Data) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: MyPlanData.Data) {
            Log.d("mlog: MyPlanAdapter", data.planId.toString())
            binding.model = data
            binding.ivMyPlanSpot.clipToOutline = true
            clickItem(data)
            clickScrap(data)
        }

        private fun clickItem(data: MyPlanData.Data) {
            binding.root.setOnClickListener {
                itemClick(data)
            }
        }

        private fun clickScrap(data: MyPlanData.Data) {
            binding.layoutScrap.setOnClickListener {
                scrapClick(data)
                // TODO: 추후 null 처리 다시 해야합니다 !! 서버 확인하고서 ~_~  + 용민:아래 머선 기능인지 모르겠어서 주석처리 해놨습니다.
                /*if (data.isScrapped != null) {
                    data.isScrapped = !data.isScrapped!!
                } else {
                    data.isScrapped = false
                }*/
                data.scrapStatus = !data.scrapStatus
                binding.model = data
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExistMyPlanViewHolder {
        val binding = ItemMyPlanPurchaseListBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return ExistMyPlanViewHolder(binding, itemClick, scrapClick)
    }

    override fun onBindViewHolder(holder: ExistMyPlanViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    private class MyPlanComparator() : DiffUtil.ItemCallback<MyPlanData.Data>() {
        override fun areItemsTheSame(oldItem: MyPlanData.Data, newItem: MyPlanData.Data): Boolean {
            //Log.d("asdf", "areItemsTheSame - oldItem: ${oldItem.planId}, newItem: ${newItem.planId}")
            return oldItem.planId == newItem.planId
        }

        override fun areContentsTheSame(
            oldItem: MyPlanData.Data,
            newItem: MyPlanData.Data
        ): Boolean {
            return oldItem == newItem
        }
    }
}