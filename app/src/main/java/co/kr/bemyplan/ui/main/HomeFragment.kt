package co.kr.bemyplan.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import co.kr.bemyplan.R
import co.kr.bemyplan.data.TempHomeData
import co.kr.bemyplan.databinding.FragmentHomeBinding
import co.kr.bemyplan.ui.main.adapter.HomeAdapter

class HomeFragment : Fragment() {

    private lateinit var homeAdapter: HomeAdapter
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)

        initAdapter1()
        initAdapter2()

        return binding.root
    }

    private fun initAdapter1(){
        homeAdapter = HomeAdapter()
        binding.rvRecent.adapter=homeAdapter
        homeAdapter.planList.addAll(
            listOf(
                TempHomeData(R.drawable.img_home_recent, "27년 제주 토박이의 히든 플레이스 투어"),
                TempHomeData(R.drawable.img_home_recent, "27년 제주 토박이의 히든 플레이스 투어"),
                TempHomeData(R.drawable.img_home_recent, "27년 제주 토박이의 히든 플레이스 투어"),
                TempHomeData(R.drawable.img_home_recent, "27년 제주 토박이의 히든 플레이스 투어"),
                TempHomeData(R.drawable.img_home_recent, "27년 제주 토박이의 히든 플레이스 투어")
            )
        )
        homeAdapter.notifyDataSetChanged()
    }

    private fun initAdapter2(){
        homeAdapter = HomeAdapter()
        binding.rvEditorSuggest.adapter=homeAdapter
        homeAdapter.planList.addAll(
            listOf(
                TempHomeData(R.drawable.img_home_editor, "푸드파이터들을 위한 찐먹킷리스트 투어"),
                TempHomeData(R.drawable.img_home_editor, "푸드파이터들을 위한 찐먹킷리스트 투어"),
                TempHomeData(R.drawable.img_home_editor, "푸드파이터들을 위한 찐먹킷리스트 투어"),
                TempHomeData(R.drawable.img_home_editor, "푸드파이터들을 위한 찐먹킷리스트 투어"),
                TempHomeData(R.drawable.img_home_editor, "푸드파이터들을 위한 찐먹킷리스트 투어")
            )
        )
        homeAdapter.notifyDataSetChanged()
    }
}