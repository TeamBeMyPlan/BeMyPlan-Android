package co.kr.bemyplan.ui.main.myplan.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import co.kr.bemyplan.data.entity.main.myplan.MyModel
import co.kr.bemyplan.databinding.ItemMyPlanPurchaseListBinding

class MyPlanAdapter: RecyclerView.Adapter<MyPlanAdapter.ExistMyPlanViewHolder>() {
    private var purchaseTourList = listOf<MyModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExistMyPlanViewHolder {
        val binding = ItemMyPlanPurchaseListBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )

        return ExistMyPlanViewHolder(binding)
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

    class ExistMyPlanViewHolder(private val binding: ItemMyPlanPurchaseListBinding)
        : RecyclerView.ViewHolder(binding.root) {
            fun onBind(data: MyModel) {
                binding.model = data
            }
    }
}