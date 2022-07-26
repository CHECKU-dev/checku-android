package com.example.nodeproject2.ui.home

import android.view.View
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.example.nodeproject2.R
import com.example.nodeproject2.base.BaseFragment
import com.example.nodeproject2.databinding.FragmentHomeBinding
import com.example.nodeproject2.repository.ScheduleRepository
import com.example.nodeproject2.ui.home.adapter.HomeAdapter
import com.example.nodeproject2.ui.home.adapter.NotificationAdapter
import com.example.nodeproject2.ui.subject.SubjectViewModel
import com.example.nodeproject2.ui.subject.adapter.SubjectAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    @Inject
    lateinit var scheduleRepository: ScheduleRepository

    private lateinit var viewPager: ViewPager2
    private lateinit var homeAdapter: HomeAdapter

    private val viewModel by viewModels<HomeViewModel>()
    private lateinit var notifcationAdapter: NotificationAdapter

    override fun doViewCreated() {

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        viewModel.getInitData()

        initViewPager()
        initRecyclerView()
        observeRecyclerView()

    }

    private fun observeRecyclerView() {
        viewModel.notificationList.observe(viewLifecycleOwner) {
            notifcationAdapter.submitList(it)
            hideLoadingDialog()
        }

        viewModel.homeErrorToastEvent.observe(viewLifecycleOwner) {
            showCustomToast("실패 실패 실패 실패")
            hideLoadingDialog()
        }
//
        viewModel.homeWaitEvent.observe(viewLifecycleOwner) {
            showLoadingDialog()
        }

    }

    private fun initRecyclerView() {
        notifcationAdapter = NotificationAdapter(viewModel)
        binding.rvNotification.adapter = notifcationAdapter
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

//        transform.addTransformer { view: View, _: Float ->
////            var v = 1 - abs(fl)
////            view.scaleY = 0.8f + v * 0.2f
//        }

        viewPager.setPageTransformer(transform)


    }


}