package co.kr.bemyplan.ui.purchase.after.adapter

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableArrayList
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import co.kr.bemyplan.data.Spot
import co.kr.bemyplan.databinding.ItemDailyContentsBinding
import co.kr.bemyplan.databinding.ItemDailyRouteBinding
import co.kr.bemyplan.util.ToastMessage
import co.kr.bemyplan.util.ToastMessage.shortToast
import co.kr.bemyplan.util.clipTo
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import java.lang.IllegalStateException
import kotlin.coroutines.coroutineContext
import kotlin.reflect.typeOf

class DailyContentsAdapter(private val viewType: Int): RecyclerView.Adapter<DailyContentsAdapter.DailyContentsViewHolder>() {

    private var spotList = listOf<Spot>()

    override fun getItemViewType(position: Int) = viewType

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = when(viewType) {
        TYPE_CONTENTS -> {
            val binding = ItemDailyContentsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
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

    override fun onBindViewHolder(holder: DailyContentsViewHolder, position: Int) {
        when(holder) {
            is ContentsViewHolder -> {
                holder.onBind(spotList[position])
            }
            is RouteViewHolder -> {
                holder.onBind(spotList[position])
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

    abstract class DailyContentsViewHolder(binding: ViewDataBinding)
        : RecyclerView.ViewHolder(binding.root) {
        abstract fun onBind(data: Spot)
    }

    class ContentsViewHolder(private val binding: ItemDailyContentsBinding, private val mContext: Context): DailyContentsViewHolder(binding) {
        override fun onBind(data: Spot) {
            binding.spot = data
            clipTo(binding.ivPhoto, data.photo)
            copyButton()
        }

        private fun copyButton() {
            binding.ivCopy.setOnClickListener {
                var clipboard = mContext.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                val clip = ClipData.newPlainText("CODE", binding.tvAddress.text)
                clipboard.setPrimaryClip(clip)
                mContext.shortToast("주소를 복사했습니다")
            }
        }
    }

    class RouteViewHolder(private val binding: ItemDailyRouteBinding): DailyContentsViewHolder(binding) {
        override fun onBind(data: Spot) {
            binding.spot = data
        }
    }

    companion object {
        const val TYPE_CONTENTS = 0
        const val TYPE_ROUTE = 1
    }
}