package co.kr.bemyplan.ui.main.location

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import co.kr.bemyplan.data.firebase.FirebaseAnalyticsProvider
import co.kr.bemyplan.databinding.ItemLocationBinding
import co.kr.bemyplan.domain.model.main.location.LocationData
import co.kr.bemyplan.util.ToastMessage.shortToast
import co.kr.bemyplan.util.clipTo
import javax.inject.Inject

class LocationAdapter(
    private val itemClick: (LocationData) -> Unit,
    private val myContext: Context,
    private val logEvent: (Boolean, String) -> Unit
) :
    ListAdapter<LocationData, LocationAdapter.LocationViewHolder>(LocationComparator()) {
    @Inject
    lateinit var firebaseAnalyticsProvider: FirebaseAnalyticsProvider

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemLocationBinding.inflate(layoutInflater, parent, false)
        return LocationViewHolder(binding, itemClick, myContext, logEvent)
    }

    override fun onBindViewHolder(holder: LocationViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    class LocationViewHolder(
        private val binding: ItemLocationBinding,
        private val itemClick: (LocationData) -> Unit,
        private val myContext: Context,
        private val logEvent: (Boolean, String) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: LocationData) {
            binding.locationItem = data
            clipTo(binding.ivLocation, data.thumbnailUrl)
            binding.root.setOnClickListener {
                if (binding.ivLock.visibility == View.GONE) {
                    firebaseAnalyticsProvider.firebaseAnalytics.logEvent(
                        "clickOpenedTravelSpot",
                        Bundle().apply {
                            putString("spot", data.name)
                        })
                    itemClick(data)
                    logEvent(true, data.name)
                } else {
                    firebaseAnalyticsProvider.firebaseAnalytics.logEvent(
                        "clickClosedTravelSpot",
                        Bundle().apply {
                            putString("spot", data.name)
                        })
                    myContext.shortToast("추후에 오픈될 예정입니다")
                    logEvent(false, data.name)
                }
            }
        }
    }

    private class LocationComparator : DiffUtil.ItemCallback<LocationData>() {
        override fun areItemsTheSame(oldItem: LocationData, newItem: LocationData): Boolean {
            return oldItem.region == newItem.region
        }

        override fun areContentsTheSame(oldItem: LocationData, newItem: LocationData): Boolean {
            return oldItem == newItem
        }
    }
}