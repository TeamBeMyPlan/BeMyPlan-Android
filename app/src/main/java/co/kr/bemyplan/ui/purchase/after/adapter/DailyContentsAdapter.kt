package co.kr.bemyplan.ui.purchase.after.adapter

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import co.kr.bemyplan.R
import co.kr.bemyplan.databinding.ItemDailyContentsBinding
import co.kr.bemyplan.databinding.ItemDailyRouteBinding
import co.kr.bemyplan.domain.model.purchase.after.Spots
import co.kr.bemyplan.util.ToastMessage.shortToast
import com.google.android.material.tabs.TabLayoutMediator

class DailyContentsAdapter(private val viewType: Int, var photoUrl: ((String) -> Unit)? = null) :
    RecyclerView.Adapter<DailyContentsAdapter.SpotViewHolder>() {
    private var _binding: ItemDailyContentsBinding? = null
    private val binding get() = _binding ?: error("Binding이 초기화 되지 않았습니다.")

    // item 갱신
    private val differCallback = object: DiffUtil.ItemCallback<Spots>() {
        override fun areItemsTheSame(oldItem: Spots, newItem: Spots): Boolean {
            return oldItem == newItem
        }
        override fun areContentsTheSame(oldItem: Spots, newItem: Spots): Boolean {
            return oldItem == newItem
        }
    }
    private val differ = AsyncListDiffer(this, differCallback)

    // fragment에서 아이템 갱신 필요한 경우 호출할 수 있도록 설정
    fun submitList(list: List<Spots>) {
        differ.submitList(list, Runnable {
            if (list.size >= 5) notifyItemChanged(4)
        })
    }

    override fun getItemViewType(position: Int) = viewType

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = when (viewType) {
        TYPE_CONTENTS -> {
            _binding = ItemDailyContentsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
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
        val spot = differ.currentList[position]

        when (holder) {
            is ContentsViewHolder -> {
                if (position == differ.currentList.size - 1) {
                    holder.onBind(spot, true)
                } else {
                    holder.onBind(spot, differ.currentList[position + 1].name)
                }
            }
            is RouteViewHolder -> {
                holder.onBind(spot, position, itemCount - 1)
            }
        }
    }

    override fun getItemCount() = differ.currentList.size

    open class SpotViewHolder(binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {
        open fun onBind(data: Spots, isLastSpot: Boolean) {}
        open fun onBind(data: Spots, nextSpot: String) {}
        open fun onBind(data: Spots, position: Int, lastPosition: Int) {}
    }

    class ContentsViewHolder(
        private val binding: ItemDailyContentsBinding,
        private val mContext: Context,
        private val photoUrl: ((String) -> Unit)?
    ) : SpotViewHolder(binding) {
        private lateinit var viewPagerAdapter: PhotoViewPagerAdapter
        override fun onBind(data: Spots, nextSpot: String) {
            binding.spot = data
            binding.nextSpot = nextSpot
            binding.isLastSpot = false
            initViewPagerAdapter(data)
            initTabLayout()
            binding.clAddress.setOnClickListener { copyButton() }
        }

        override fun onBind(data: Spot, isLastSpot: Boolean) {
            binding.isLastSpot = true
            binding.spot = data
            initViewPagerAdapter(data)
            initTabLayout()
            binding.clAddress.setOnClickListener { copyButton() }
        }

        private fun copyButton() {
            val clipboard =
                mContext.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("address", binding.tvAddress.text)
            clipboard.setPrimaryClip(clip)
            mContext.shortToast("주소를 복사했습니다")
        }

        private fun initViewPagerAdapter(data: Spot) {
            viewPagerAdapter = PhotoViewPagerAdapter(photoUrl)
            viewPagerAdapter.setItems(data.photoUrls)
            binding.vpPhoto.adapter = viewPagerAdapter
            binding.vpPhoto.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        }

        private fun initTabLayout() {
            TabLayoutMediator(binding.tlPhoto, binding.vpPhoto) { _, _ ->

            }.attach()
        }
    }

    class RouteViewHolder(private val binding: ItemDailyRouteBinding) : SpotViewHolder(binding) {
        override fun onBind(data: Spot, position: Int, lastPosition: Int) {
            binding.spot = data
            binding.position = position
            binding.lastPosition = lastPosition
            chooseImg(data)
        }

        private fun chooseImg(data: Spot) {
            if (data.nextSpotMobility == "버스" || data.nextSpotMobility == "지하철") {
                binding.ivTransportation.setImageResource(R.drawable.ic_icn_public_transport)
            } else if (data.nextSpotMobility == "택시" || data.nextSpotMobility == "승용차") {
                binding.ivTransportation.setImageResource(R.drawable.ic_icn_car)
            } else if (data.nextSpotMobility == "도보") {
                binding.ivTransportation.setImageResource(R.drawable.ic_icn_walk)
            } else {
                binding.ivTransportation.isVisible = false
            }
        }
    }

    companion object {
        const val TYPE_CONTENTS = 0
        const val TYPE_ROUTE = 1
    }
}