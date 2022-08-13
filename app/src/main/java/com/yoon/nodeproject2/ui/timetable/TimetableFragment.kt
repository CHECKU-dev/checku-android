package com.yoon.nodeproject2.ui.timetable

import android.annotation.SuppressLint
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import com.yoon.nodeproject2.R
import com.yoon.nodeproject2.base.BaseFragment
import com.yoon.nodeproject2.databinding.FragmentTimeTableBinding
import com.yoon.nodeproject2.ui.timetable.adapter.TimeTableAdapter
import com.yoon.nodeproject2.widget.utils.FAVORITE_CANCEL_SUCCESS_MESSAGE
import com.yoon.nodeproject2.widget.utils.NETWORK_ERROR_MESSAGE
import com.yoon.nodeproject2.widget.utils.NOTIFICATION_ADD_SUCCESS_MESSAGE
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TimetableFragment : BaseFragment<FragmentTimeTableBinding>(R.layout.fragment_time_table) {
    private val viewModel by viewModels<TimeTableViewModel>()

    private lateinit var timeTableAdapter: TimeTableAdapter
    private lateinit var swipeHelperCallback: SwipeHelperCallback

    companion object {
        fun newInstance() = TimetableFragment()
        const val TAG = "TimetableFragment"
    }

    override fun doViewCreated() {

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        initRecyclerView()
        observeRecyclerView()

    }

    private fun observeRecyclerView() {
        viewModel.subjectList.observe(viewLifecycleOwner) {
            timeTableAdapter.submitList(it)
            if (it.size == 0) {
                binding.emptyView.visibility = View.VISIBLE
            } else {
                binding.emptyView.visibility = View.GONE
            }
            hideLoadingDialog()
        }

        viewModel.timeTableErrorToastEvent.observe(viewLifecycleOwner) {
            showCustomToast(NETWORK_ERROR_MESSAGE)
            hideLoadingDialog()
        }

        viewModel.timeTableWaitEvent.observe(viewLifecycleOwner) {
            showLoadingDialog()
        }

        viewModel.subjectRemoveSuccessEvent.observe(viewLifecycleOwner) {
            showCustomToast(FAVORITE_CANCEL_SUCCESS_MESSAGE)
        }

        viewModel.notificationApplySuccessEvent.observe(viewLifecycleOwner) {
            showCustomToast(NOTIFICATION_ADD_SUCCESS_MESSAGE)
            hideLoadingDialog()

        }

        viewModel.notificationErrorToastEvent.observe(viewLifecycleOwner) {
            showCustomToast(it)
            hideLoadingDialog()
        }

    }

    @SuppressLint("ClickableViewAccessibility") // warning 무시용
    private fun initRecyclerView() {

        timeTableAdapter = TimeTableAdapter(viewModel)
        binding.rvFavorite.adapter = timeTableAdapter

        swipeHelperCallback = SwipeHelperCallback().apply {
            setClamp(resources.displayMetrics.widthPixels.toFloat() / 2.5)
        }
        val itemTouchHelper = ItemTouchHelper(swipeHelperCallback)
        itemTouchHelper.attachToRecyclerView(binding.rvFavorite)

        binding.rvFavorite.apply {
            setOnTouchListener { _, _ ->
                swipeHelperCallback.removePreviousClamp(this)
                false
            }
        }

    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (hidden) {
            swipeHelperCallback.closeClamp(binding.rvFavorite)
        } else {
            viewModel.getInitData()
        }
    }
}