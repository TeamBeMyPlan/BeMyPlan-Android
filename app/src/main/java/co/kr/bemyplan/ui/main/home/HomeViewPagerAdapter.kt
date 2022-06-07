package co.kr.bemyplan.ui.main.home

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import co.kr.bemyplan.R
import co.kr.bemyplan.databinding.ItemPopularBinding
import co.kr.bemyplan.domain.model.main.home.HomeDomainData
import co.kr.bemyplan.util.clipTo

class HomeViewPagerAdapter(val itemClick: (HomeDomainData) -> Unit) :
    RecyclerView.Adapter<HomeViewPagerAdapter.PagerViewHolder>() {
    val planList = mutableListOf<HomeDomainData>()

    class PagerViewHolder(
        private val binding: ItemPopularBinding,
        private val itemClick: (HomeDomainData) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: HomeDomainData) {
            binding.popularityItem = data
            clipTo(binding.ivPopular, data.thumbnailUrl)
            clickItem(data)
        }

        private fun clickItem(data: HomeDomainData) {
            binding.root.setOnClickListener {
                itemClick(data)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerViewHolder {
        val binding: ItemPopularBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_popular,
            parent,
            false
        )
        return PagerViewHolder(binding, itemClick)
    }

    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        holder.onBind(planList[position])
    }

    override fun getItemCount(): Int {
        return planList.size
    }
}