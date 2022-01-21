package co.kr.bemyplan.ui.purchase.after.adapter

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableArrayList
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import co.kr.bemyplan.R
import co.kr.bemyplan.data.entity.purchase.after.Spot
import co.kr.bemyplan.databinding.FragmentDailyContentsBinding
import co.kr.bemyplan.databinding.ItemDailyContentsBinding
import co.kr.bemyplan.databinding.ItemDailyRouteBinding
import co.kr.bemyplan.ui.purchase.PurchaseActivity
import co.kr.bemyplan.util.ToastMessage.shortToast
import co.kr.bemyplan.util.clipTo
import com.google.android.material.tabs.TabLayoutMediator
import java.lang.IllegalStateException

class DailyContentsAdapter(private val viewType: Int, val photoUrl: (String) -> Unit): RecyclerView.Adapter<DailyContentsAdapter.SpotViewHolder>() {
    private var _binding: ItemDailyContentsBinding? = null
    private val binding get() = _binding ?: error("Binding이 초기화 되지 않았습니다.")

    private var spotList = listOf<Spot>()

    override fun getItemViewType(position: Int) = viewType

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = when(viewType) {
        TYPE_CONTENTS -> {
            _binding = ItemDailyContentsBinding.inflate(LayoutInflater.from(parent.context),
                parent, false)
            ContentsViewHolder(binding, parent.context, photoUrl)
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
                if (position == spotList.size - 1) {
                    holder.onBind(spotList[position], true)
                }
                else {
                    holder.onBind(spotList[position], spotList[position + 1].title)
                }
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
        open fun onBind(data: Spot, isLastSpot: Boolean) {}
        open fun onBind(data: Spot, nextSpot: String) {}
        open fun onBind(data: Spot, position: Int, lastPosition: Int) {}
    }

    class ContentsViewHolder(private val binding: ItemDailyContentsBinding, private val mContext: Context, private val photoUrl: (String) -> Unit): SpotViewHolder(binding) {
        private lateinit var viewPagerAdapter: PhotoViewPagerAdapter
        override fun onBind(data: Spot, nextSpot: String) {
            binding.spot = data
            binding.nextSpot = nextSpot
            binding.isLastSpot = false
            initViewPagerAdapter(data)
            initTabLayout()
            copyButton()
        }

        override fun onBind(data: Spot, isLastSpot: Boolean) {
            binding.isLastSpot = true
            binding.spot = data
            initViewPagerAdapter(data)
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

        private fun initViewPagerAdapter(data: Spot) {
            viewPagerAdapter = PhotoViewPagerAdapter(photoUrl = {
                Log.d("hoooni","asdfasdf")
                photoUrl
            })
            viewPagerAdapter.setItems(data.photoUrls)
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
            chooseImg(data)
        }

        private fun chooseImg(data: Spot) {
            if (data.nextSpotMobility == "버스" || data.nextSpotMobility == "지하철") {
                binding.ivTransportation.setImageResource(R.drawable.ic_icn_public_transport)
            }
            else if (data.nextSpotMobility == "택시" || data.nextSpotMobility == "승용차") {
                binding.ivTransportation.setImageResource(R.drawable.ic_icn_car)
            }
            else if (data.nextSpotMobility == "도보") {
                binding.ivTransportation.setImageResource(R.drawable.ic_icn_walk)
            }
            else {
                binding.ivTransportation.isVisible = false
            }
        }
    }

    companion object {
        const val TYPE_CONTENTS = 0
        const val TYPE_ROUTE = 1
    }
}