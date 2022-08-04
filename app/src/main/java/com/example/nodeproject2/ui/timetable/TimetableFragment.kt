package com.example.nodeproject2.ui.timetable

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import com.example.nodeproject2.R
import com.example.nodeproject2.base.BaseFragment
import com.example.nodeproject2.databinding.FragmentTimeTableBinding
import com.example.nodeproject2.ui.timetable.adapter.TimeTableAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TimetableFragment : BaseFragment<FragmentTimeTableBinding>(R.layout.fragment_time_table) {
    private val viewModel by viewModels<TimeTableViewModel>()
//    private val viewModel by activityViewModels<TimeTableViewModel>()

    private lateinit var timeTableAdapter: TimeTableAdapter
    private lateinit var swipeHelperCallback:SwipeHelperCallback
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
            showCustomToast("실패 실패 실패 실패")
            hideLoadingDialog()

        }

        viewModel.timeTableWaitEvent.observe(viewLifecycleOwner) {
            showLoadingDialog()
        }

        viewModel.subjectRemoveSuccessEvent.observe(viewLifecycleOwner) {
            showCustomToast("즐겨찾기 삭제 성공!!!")
        }

        viewModel.notificationApplySuccessEvent.observe(viewLifecycleOwner) {
            showCustomToast("알림 신청 성공!!!")
        }

    }

    @SuppressLint("ClickableViewAccessibility") // warning 무시용
    private fun initRecyclerView() {

        timeTableAdapter = TimeTableAdapter(viewModel)
        binding.rvFavorite.adapter = timeTableAdapter

        swipeHelperCallback = SwipeHelperCallback().apply {
            setClamp(resources.displayMetrics.widthPixels.toFloat() / 3)
        }
        val itemTouchHelper = ItemTouchHelper(swipeHelperCallback)
        itemTouchHelper.attachToRecyclerView(binding.rvFavorite)

        binding.rvFavorite.apply {
            setOnTouchListener { _, _ ->
                swipeHelperCallback.removePreviousClamp(this)
                false
            }
        }
//        timeTableAdapter.setHasStableIds(true)

    }

    // TODO 데이터 새로 추가 시 열려있음;
    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if(hidden) {
//            swipeHelperCallback.currentPosition = null
            swipeHelperCallback.closeClamp(binding.rvFavorite)
        }else {
            viewModel.getInitData()
        }
    }
}