package co.kr.bemyplan.ui.purchase.after.adapter

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableArrayList
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import co.kr.bemyplan.data.entity.purchase.after.Spot
import co.kr.bemyplan.databinding.FragmentDailyContentsBinding
import co.kr.bemyplan.databinding.ItemDailyContentsBinding
import co.kr.bemyplan.databinding.ItemDailyRouteBinding
import co.kr.bemyplan.util.ToastMessage.shortToast
import co.kr.bemyplan.util.clipTo
import com.google.android.material.tabs.TabLayoutMediator
import java.lang.IllegalStateException

class DailyContentsAdapter(private val viewType: Int): RecyclerView.Adapter<DailyContentsAdapter.SpotViewHolder>() {
    private var _binding: ItemDailyContentsBinding? = null
    private val binding get() = _binding ?: error("Binding이 초기화 되지 않았습니다.")

    private var spotList = listOf<Spot>()

    override fun getItemViewType(position: Int) = viewType

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = when(viewType) {
        TYPE_CONTENTS -> {
            _binding = ItemDailyContentsBinding.inflate(LayoutInflater.from(parent.context),
                parent, false)
            ContentsViewHolder(binding, parent.context)
        }
        TYPE_ROUTE -> {
            val binding = ItemDailyRouteBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
            RouteViewHolder(binding)
        }
        else -> {
            throw IllegalStateException("Not Found ViewHolder Type $viewType")
        }
    }

    override fun onBindViewHolder(holder: SpotViewHolder, position: Int) {
        when(holder) {
            is ContentsViewHolder -> {
                holder.onBind(spotList[position])
            }
            is RouteViewHolder -> {
                holder.onBind(spotList[position], position, itemCount - 1)
            }
        }
    }

    override fun getItemCount() = spotList.size

    @SuppressLint("NotifyDataSetChanged")
    fun setItems(items: List<Spot>) {
        spotList = items
        notifyDataSetChanged()
    }

    // MVVM을 위한 코드
   @BindingAdapter("bind:spotList")
    fun bindSpotList(recyclerView: RecyclerView, items: ObservableArrayList<Spot>) {
        val adapter = recyclerView.adapter as? DailyContentsAdapter
        adapter?.setItems(items)
    }

    open class SpotViewHolder(binding: ViewDataBinding)
        : RecyclerView.ViewHolder(binding.root) {
        open fun onBind(data: Spot) {}
        open fun onBind(data: Spot, position: Int, lastPosition: Int) {}
    }

    class ContentsViewHolder(private val binding: ItemDailyContentsBinding, private val mContext: Context): SpotViewHolder(binding) {
        private lateinit var viewPagerAdapter: PhotoViewPagerAdapter
        override fun onBind(data: Spot) {
            binding.spot = data
            initViewPagerAdapter()
            initTabLayout()
            copyButton()
        }

        private fun copyButton() {
            binding.ivCopy.setOnClickListener {
                val clipboard = mContext.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                val clip = ClipData.newPlainText("CODE", binding.tvAddress.text)
                clipboard.setPrimaryClip(clip)
                mContext.shortToast("주소를 복사했습니다")
            }
        }

        private fun initViewPagerAdapter() {
            viewPagerAdapter = PhotoViewPagerAdapter()
            viewPagerAdapter.setItems(binding.spot!!.photo_urls)
            binding.vpPhoto.adapter = viewPagerAdapter
            binding.vpPhoto.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        }

        private fun initTabLayout() {
            TabLayoutMediator(binding.tlPhoto, binding.vpPhoto) { _, _ ->

            }.attach()
        }
    }

    class RouteViewHolder(private val binding: ItemDailyRouteBinding): SpotViewHolder(binding) {
        override fun onBind(data: Spot, position: Int, lastPosition: Int) {
            binding.spot = data
            binding.position = position
            binding.lastPosition = lastPosition
        }
    }

    companion object {
        const val TYPE_CONTENTS = 0
        const val TYPE_ROUTE = 1
    }
}