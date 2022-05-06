package co.kr.bemyplan.ui.main.myplan.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import co.kr.bemyplan.data.entity.main.myplan.MyModel
import co.kr.bemyplan.databinding.ItemMyPlanPurchaseListBinding
import co.kr.bemyplan.util.clipTo

class MyPlanAdapter(
    private val itemClick: (MyModel) -> Unit,
    private val scrapClick: (MyModel) -> Unit
) :
    RecyclerView.Adapter<MyPlanAdapter.ExistMyPlanViewHolder>() {
    private var purchaseTourList = listOf<MyModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExistMyPlanViewHolder {
        val binding = ItemMyPlanPurchaseListBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )

        return ExistMyPlanViewHolder(binding, itemClick, scrapClick)
    }

    override fun onBindViewHolder(holder: ExistMyPlanViewHolder, position: Int) {
        holder.onBind(purchaseTourList[position])
    }

    override fun getItemCount() = purchaseTourList.size

    @SuppressLint("NotifyDataSetChanged")
    fun setItems(items: List<MyModel>) {
        purchaseTourList = items
        notifyDataSetChanged()
    }

    class ExistMyPlanViewHolder(
        private val binding: ItemMyPlanPurchaseListBinding,
        private val itemClick: (MyModel) -> Unit,
        private val scrapClick: (MyModel) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: MyModel) {
            Log.d("mlog: MyPlanAdapter", data.postId.toString())
            binding.model = data
            binding.ivMyPlanSpot.clipToOutline = true
            clickItem(data)
            clickScrap(data)
        }

        private fun clickItem(data: MyModel) {
            binding.root.setOnClickListener {
                itemClick(data)
            }
        }

        private fun clickScrap(data: MyModel) {
            binding.layoutScrap.setOnClickListener {
                scrapClick(data)
                // TODO: 추후 null 처리 다시 해야합니다 !! 서버 확인하고서 ~_~
                if (data.isScrapped != null) {
                    data.isScrapped = !data.isScrapped!!
                } else {
                    data.isScrapped = false
                }
                binding.model = data
            }
        }
    }
}