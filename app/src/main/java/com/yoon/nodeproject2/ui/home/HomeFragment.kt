package com.yoon.nodeproject2.ui.home

import androidx.fragment.app.viewModels
import com.yoon.nodeproject2.R
import com.yoon.nodeproject2.base.BaseFragment
import com.yoon.nodeproject2.databinding.FragmentHomeBinding
import com.yoon.nodeproject2.repository.ScheduleRepository
import com.yoon.nodeproject2.ui.home.adapter.HomeAdapter
import com.yoon.nodeproject2.ui.home.adapter.NotificationAdapter
import com.yoon.nodeproject2.widget.utils.NETWORK_ERROR_MESSAGE
import com.yoon.nodeproject2.widget.utils.NOTIFICATION_CANCEL_SUCCESS_MESSAGE
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    @Inject
    lateinit var scheduleRepository: ScheduleRepository

    private lateinit var homeAdapter: HomeAdapter

    private val viewModel by viewModels<HomeViewModel>()
    private lateinit var notificationAdapter: NotificationAdapter
    private lateinit var dialogFragment: HomeNotificationDialog


    companion object {
        fun newInstance() = HomeFragment()
        const val TAG = "HomeFragment"
    }

    override fun doViewCreated() {

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        dialogFragment = HomeNotificationDialog(viewModel)


        viewModel.getInitData()

        initRecyclerView()
        observeRecyclerView()

    }

    private fun observeRecyclerView() {
        viewModel.notificationList.observe(viewLifecycleOwner) {
            notificationAdapter.submitList(it)
            hideLoadingDialog()
        }

        viewModel.homeErrorToastEvent.observe(viewLifecycleOwner) {
            showCustomToast(NETWORK_ERROR_MESSAGE)
            hideLoadingDialog()
        }

        viewModel.homeWaitEvent.observe(viewLifecycleOwner) {
            showLoadingDialog()
        }

        viewModel.homeSuccessToastEvent.observe(viewLifecycleOwner) {
            dialogFragment.dismiss()
            showCustomToast(NOTIFICATION_CANCEL_SUCCESS_MESSAGE)
        }


        viewModel.dialogShowEvent.observe(viewLifecycleOwner) {
            dialogFragment.show(requireActivity().supportFragmentManager, dialogFragment.tag)
        }

        viewModel.dialogDismissEvent.observe(viewLifecycleOwner) {
            dialogFragment.dismiss()
        }


    }

    private fun initRecyclerView() {
        val schedule = scheduleRepository.getSchedule()

        notificationAdapter = NotificationAdapter(viewModel)
        binding.rvNotification.adapter = notificationAdapter

        homeAdapter = HomeAdapter(context!!, schedule)
        binding.rvSchedule.adapter = homeAdapter

    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (hidden) {
        } else {
            viewModel.getInitData()
        }
    }


}