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
import co.kr.bemyplan.domain.model.purchase.after.SpotsWithAddress
import co.kr.bemyplan.domain.model.purchase.after.moveInfo.Infos
import co.kr.bemyplan.util.ToastMessage.shortToast
import com.google.android.material.tabs.TabLayoutMediator


class DailyContentsAdapter(private val viewType: Int, var photoUrl: ((String) -> Unit)? = null) :
    RecyclerView.Adapter<DailyContentsAdapter.SpotViewHolder>() {
    private var _binding: ItemDailyContentsBinding? = null
    private val binding get() = _binding ?: error("Binding이 초기화 되지 않았습니다.")

    // item 갱신
    private val differCallback = object: DiffUtil.ItemCallback<Pair<Infos?, SpotsWithAddress?>>() {
        override fun areItemsTheSame(oldItem: Pair<Infos?, SpotsWithAddress?>, newItem: Pair<Infos?, SpotsWithAddress?>): Boolean {
            return oldItem == newItem
        }
        override fun areContentsTheSame(oldItem: Pair<Infos?, SpotsWithAddress?>, newItem: Pair<Infos?, SpotsWithAddress?>): Boolean {
            return oldItem == newItem
        }
    }
    private val differ = AsyncListDiffer(this, differCallback)

    // fragment에서 아이템 갱신 필요한 경우 호출할 수 있도록 설정
    fun submitList(list: List<Pair<Infos?, SpotsWithAddress?>>) {
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
        val spots = differ.currentList[position]
        when (holder) {
            is ContentsViewHolder -> {
                if (position == differ.currentList.size - 1) {
                    holder.onBind(spots,true)
                } else {
                    holder.onBind(spots, differ.currentList[position + 1].second!!.name)
                }
            }
            is RouteViewHolder -> {
                holder.onBind(spots, position, itemCount - 1)
            }
        }
    }

    override fun getItemCount() = differ.currentList.size

    open class SpotViewHolder(binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {
        open fun onBind(data: Pair<Infos?, SpotsWithAddress?>, isLastSpot: Boolean) {}
        open fun onBind(data: Pair<Infos?, SpotsWithAddress?>, nextSpot: String) {}
        open fun onBind(data: Pair<Infos?, SpotsWithAddress?>, position: Int, lastPosition: Int) {}
    }

    class ContentsViewHolder(
        private val binding: ItemDailyContentsBinding,
        private val mContext: Context,
        private val photoUrl: ((String) -> Unit)?
    ) : SpotViewHolder(binding) {
        private lateinit var viewPagerAdapter: PhotoViewPagerAdapter
        override fun onBind(data: Pair<Infos?, SpotsWithAddress?>, nextSpot: String) {
            binding.spots = data.second
            binding.infos = data.first
            binding.nextSpot = nextSpot
            binding.isLastSpot = false
            binding.tvAddress.text = data.second?.address
            data.first?.let { setMobilityToKorean(it) }
            binding.isTipAvailable = data.second!!.tip.isNullOrEmpty()
            initViewPagerAdapter(data)
            initTabLayout()
            binding.clAddress.setOnClickListener { copyButton() }
        }

        override fun onBind(data: Pair<Infos?, SpotsWithAddress?>, isLastSpot: Boolean) {
            binding.isLastSpot = true
            binding.spots = data.second
            binding.infos = data.first
            binding.tvAddress.text = data.second?.address
            binding.isTipAvailable = data.second!!.tip.isNullOrEmpty()
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

        private fun setMobilityToKorean(data: Infos) {
            if (data.mobility == "PUBLIC") {
                binding.tvMoving.text = "대중교통"
            } else if (data.mobility == "CAR") {
                binding.tvMoving.text = "차량"
            } else if (data.mobility == "WALK") {
                binding.tvMoving.text = "도보"
            } else if (data.mobility == "BICYCLE") {
                binding.tvMoving.text = "자전거"
            }
            else {
                binding.tvMoving.text = ""
            }
        }

        private fun initViewPagerAdapter(data: Pair<Infos?, SpotsWithAddress?>) {
            viewPagerAdapter = PhotoViewPagerAdapter(photoUrl)
            viewPagerAdapter.setItems(data.second!!.images)
            binding.vpPhoto.adapter = viewPagerAdapter
            binding.vpPhoto.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        }

        private fun initTabLayout() {
            TabLayoutMediator(binding.tlPhoto, binding.vpPhoto) { _, _ ->

            }.attach()
        }
    }

    class RouteViewHolder(private val binding: ItemDailyRouteBinding) : SpotViewHolder(binding) {
        override fun onBind(data: Pair<Infos?, SpotsWithAddress?>, position: Int, lastPosition: Int) {
            binding.spots = data.second
            binding.infos = data.first
            binding.position = position
            binding.lastPosition = lastPosition
            data.first?.let {
                chooseImg(it)
            }
        }

        private fun chooseImg(data: Infos) {
            if (data.mobility == "PUBLIC") {
                binding.ivTransportation.setImageResource(R.drawable.ic_icn_public_transport)
            } else if (data.mobility == "CAR") {
                binding.ivTransportation.setImageResource(R.drawable.ic_icn_car)
            } else if (data.mobility == "WALK") {
                binding.ivTransportation.setImageResource(R.drawable.ic_icn_walk)
            } else if (data.mobility == "BICYCLE") {
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