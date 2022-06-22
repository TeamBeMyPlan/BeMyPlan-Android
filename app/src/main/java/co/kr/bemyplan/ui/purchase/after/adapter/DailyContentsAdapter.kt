package co.kr.bemyplan.ui.purchase.after.adapter

import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import co.kr.bemyplan.R
import co.kr.bemyplan.databinding.ItemDailyContentsBinding
import co.kr.bemyplan.databinding.ItemDailyRouteBinding
import co.kr.bemyplan.domain.model.purchase.after.SpotsWithAddress
import co.kr.bemyplan.domain.model.purchase.after.moveInfo.Infos
import co.kr.bemyplan.ui.purchase.after.AfterPurchaseActivity
import co.kr.bemyplan.util.ToastMessage.shortToast
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.NonDisposableHandle.parent
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapReverseGeoCoder
import timber.log.Timber
import java.lang.IndexOutOfBoundsException
import java.util.*


class DailyContentsAdapter(private val viewType: Int, val activity: FragmentActivity?, var photoUrl: ((String) -> Unit)? = null) :
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
            ContentsViewHolder(binding, parent.context, activity, photoUrl)
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
        private val mContext: Context, val activity: FragmentActivity?,
        private val photoUrl: ((String) -> Unit)?
    ) : SpotViewHolder(binding) {
        private lateinit var viewPagerAdapter: PhotoViewPagerAdapter

        // 안드로이드 주소 검색
        private fun getAddressFromGeoCode(latitude: Double, longitude: Double) : String {
            val geoCoder = Geocoder(mContext, Locale.KOREA)
            val address: Address
            // 안드로이드 지도로 주소 검색
            try {
                address = geoCoder.getFromLocation(latitude, longitude, 1)[0]
            } catch (e: IndexOutOfBoundsException) {
                // 후에 adapter에서 카카오 api로 주소 변환
                return "주소를 찾을 수 없습니다"
            }
            val result = StringBuilder().apply {
                var index = 0
                var line: String? = ""
                while(line != null) {
                    line = address.getAddressLine(index)
                    line = line?.replace("대한민국 ", "")
                    index++
                    append(line?: "")
                }
            }
            return result.toString()
        }

        // 주소 검색
        private fun getAddress(data: Pair<Infos?, SpotsWithAddress?>) {
            getAddressFromGeoCode(data.second!!.latitude, data.second!!.longitude)

            if (data.second?.address.equals("주소를 찾을 수 없습니다")) {
                val ai: ApplicationInfo = this.mContext.packageManager.getApplicationInfo(
                    this.mContext.packageName,
                    PackageManager.GET_META_DATA
                )
                if (ai.metaData != null) {
                    val metaData: String? = ai.metaData.getString("com.kakao.sdk.AppKey")
                    val currentMapPoint = MapPoint.mapPointWithGeoCoord(
                        data.second!!.latitude,
                        data.second!!.longitude
                    )
                    val mapReverseGeoCoder = MapReverseGeoCoder(
                        metaData,
                        currentMapPoint,
                        object : MapReverseGeoCoder.ReverseGeoCodingResultListener {
                            override fun onReverseGeoCoderFoundAddress(
                                p0: MapReverseGeoCoder?,
                                address: String
                            ) {
                                // 주소 받아오기 성공 - address: 현재 주소
                                binding.tvAddress.text = address
                            }
                            override fun onReverseGeoCoderFailedToFindAddress(p0: MapReverseGeoCoder?) {
                                // 주소 받아오기 실패
                                Timber.tag("MapReverseGeoCoder").d("Can't get address from map point")
                            }
                        },
                        activity
                    )
                    mapReverseGeoCoder.startFindingAddress()
                }
            } else {
                binding.tvAddress.text = data.second?.address
            }
        }

        override fun onBind(data: Pair<Infos?, SpotsWithAddress?>, nextSpot: String) {
            binding.spots = data.second
            binding.infos = data.first
            binding.nextSpot = nextSpot
            binding.isLastSpot = false

            getAddress(data)

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

            getAddress(data)

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