package com.example.nodeproject2.ui.home

import android.view.View
import androidx.core.view.get
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.example.nodeproject2.R
import com.example.nodeproject2.base.BaseFragment
import com.example.nodeproject2.databinding.FragmentHomeBinding
import com.example.nodeproject2.repository.ScheduleRepository
import dagger.hilt.android.AndroidEntryPoint
import java.lang.Math.abs
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    @Inject
    lateinit var scheduleRepository: ScheduleRepository

    private lateinit var viewPager: ViewPager2
    private lateinit var homeAdapter: HomeAdapter

    override fun doViewCreated() {

        initViewPager()

//        viewPager.offscreenPageLimit = 3
//        var transform = CompositePageTransformer()
//        transform.addTransformer(MarginPageTransformer(8))
//
//        transform.addTransformer({ view: View, fl: Float ->
//            var v = 1-Math.abs(fl)
//            view.scaleY = 0.8f + v * 0.2f
//        })

//        binding.vpHome.setPageTransformer(transform)

    }

    private fun initViewPager() {
        val schedule = scheduleRepository.getSchedule()

        viewPager = binding.vpHome
        homeAdapter = HomeAdapter(context!!, schedule)
        viewPager.adapter = homeAdapter

        viewPager.offscreenPageLimit = 2
        viewPager.getChildAt(0).overScrollMode = View.OVER_SCROLL_NEVER
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                homeAdapter.setPageSelected(position)
                homeAdapter.notifyDataSetChanged()
            }

        })

        var transform = CompositePageTransformer()
        transform.addTransformer(MarginPageTransformer(8))

//
//        transform.addTransformer { view: View, _: Float ->
//            println("===============================")
//            view.setBackgroundResource(R.drawable.bg_fill_main_color_round_20)
////            var v = 1 - abs(fl)
////            view.scaleY = 0.8f + v * 0.2f
//        }

        viewPager.setPageTransformer(transform)


    }


}