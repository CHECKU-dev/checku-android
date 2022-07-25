package com.example.nodeproject2.ui.home

import android.view.View
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.example.nodeproject2.R
import com.example.nodeproject2.base.BaseFragment
import com.example.nodeproject2.databinding.FragmentHomeBinding
import com.example.nodeproject2.repository.ScheduleRepository
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    @Inject
    lateinit var scheduleRepository: ScheduleRepository

    lateinit var viewPager: ViewPager2

    override fun doViewCreated() {

        val schedule = scheduleRepository.getSchedule()
        viewPager = binding.vpHome
        viewPager.adapter = HomeAdapter(
            context!!, schedule
        )
        viewPager.offscreenPageLimit = 3
        var transform = CompositePageTransformer()
        transform.addTransformer(MarginPageTransformer(8))

        transform.addTransformer({ view: View, fl: Float ->
            var v = 1-Math.abs(fl)
            view.scaleY = 0.8f + v * 0.2f
        })

        binding.vpHome.setPageTransformer(transform)



    }


}