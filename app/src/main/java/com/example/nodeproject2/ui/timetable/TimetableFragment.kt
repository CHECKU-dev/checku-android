package com.example.nodeproject2.ui.timetable

import android.annotation.SuppressLint
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import com.example.nodeproject2.R
import com.example.nodeproject2.base.BaseFragment
import com.example.nodeproject2.databinding.FragmentTimeTableBinding
import com.example.nodeproject2.ui.timetable.adapter.TimeTableAdapter
import com.example.nodeproject2.widget.utils.FAVORITE_CANCEL_SUCCESS_MESSAGE
import com.example.nodeproject2.widget.utils.NETWORK_ERROR_MESSAGE
import com.example.nodeproject2.widget.utils.NOTIFICATION_ADD_SUCCESS_MESSAGE
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
            showCustomToast(NOTIFICATION_ADD_SUCCESS_MESSAGE)
        }

        viewModel.notificationApplySuccessEvent.observe(viewLifecycleOwner) {
            showCustomToast(FAVORITE_CANCEL_SUCCESS_MESSAGE)
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