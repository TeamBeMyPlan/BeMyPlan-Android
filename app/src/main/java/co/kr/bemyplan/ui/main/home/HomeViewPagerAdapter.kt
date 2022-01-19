package co.kr.bemyplan.ui.main.home

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.marginBottom
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import co.kr.bemyplan.R
import co.kr.bemyplan.data.entity.main.home.ResponseHomePopularData
import co.kr.bemyplan.data.entity.main.home.TempHomeData
import co.kr.bemyplan.databinding.ItemPopularBinding
import co.kr.bemyplan.util.clipTo
import com.bumptech.glide.Glide

class HomeViewPagerAdapter(val itemClick: (ResponseHomePopularData.Data) -> Unit) :
    RecyclerView.Adapter<HomeViewPagerAdapter.PagerViewHolder>() {

    val planList = mutableListOf<ResponseHomePopularData.Data>()

    inner class PagerViewHolder(private val binding:ItemPopularBinding):RecyclerView.ViewHolder(binding.root){
        fun onBind(data:ResponseHomePopularData.Data){

            binding.popularityItem=data
            Log.d("yongminPopularImage", "잘 들어오나")
            clipTo(binding.ivPopular, data.thumbnail_url)

//            if(binding.tvPopularContent.isLaidOut){
//                val lineCount = binding.tvPopularContent.lineCount
//                Log.d("yongminLineCount2", "$lineCount")
//                val param = binding.tvPopularContent.layoutParams as ViewGroup.MarginLayoutParams
//
//                if(lineCount>1){
//                    param.setMargins(24,0,24,10)
//                    binding.tvPopularContent.layoutParams = param
//                }else{
//                    param.setMargins(24,0,24,30)
//                    binding.tvPopularContent.layoutParams = param
//                }
//            }
//            else{
//                Log.d("yongminLineCount2", "laidout안됨")
//            }


//            binding.tvPopularContent.post {
//
//                val lineCount: Int = binding.tvPopularContent.lineCount
//                Log.d("yongmin", "$lineCount")
//                Log.d("yongmin", binding.tvPopularContent.text.toString())
//            }



            clickItem(data)
        }

        private fun clickItem(data: ResponseHomePopularData.Data) {
            binding.root.setOnClickListener {
                itemClick(data)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerViewHolder {
        val binding : ItemPopularBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_popular, parent, false)
        return PagerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        holder.onBind(planList[position])
    }

    override fun getItemCount(): Int = planList.size

}