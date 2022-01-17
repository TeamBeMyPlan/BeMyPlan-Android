package co.kr.bemyplan.ui.main.home

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.marginBottom
import androidx.recyclerview.widget.RecyclerView
import co.kr.bemyplan.data.home.TempHomeData
import co.kr.bemyplan.databinding.ItemPopularBinding
import co.kr.bemyplan.util.clipTo
import com.bumptech.glide.Glide

class HomeViewPagerAdapter(val itemClick: (TempHomeData) -> Unit) :
    RecyclerView.Adapter<HomeViewPagerAdapter.PagerViewHolder>() {

    val planList = mutableListOf<TempHomeData>()

    inner class PagerViewHolder(private val binding:ItemPopularBinding):RecyclerView.ViewHolder(binding.root){
        fun onBind(data:TempHomeData){
            binding.tvPopularPlan.text = data.text
            binding.tvPopularContent.text = data.text2
            Glide.with(binding.ivPopular.context).load(data.img).into(binding.ivPopular)
            //clipTo(binding.ivPopular, data.img)x`

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
            binding.tvPopularContent.post {

                val lineCount: Int = binding.tvPopularContent.lineCount
                Log.d("yongmin", "$lineCount")
                Log.d("yongmin", binding.tvPopularContent.text.toString())
            }



            clickItem(data)
        }

        private fun clickItem(data: TempHomeData) {
            binding.root.setOnClickListener {
                itemClick(data)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerViewHolder {
        val binding = ItemPopularBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PagerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        holder.onBind(planList[position])
    }

    override fun getItemCount(): Int = planList.size

}