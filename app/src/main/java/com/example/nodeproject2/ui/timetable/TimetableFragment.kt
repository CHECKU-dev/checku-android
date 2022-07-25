package com.example.nodeproject2.ui.timetable

import android.annotation.SuppressLint
import android.view.View
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
    private lateinit var timeTableAdapter: TimeTableAdapter


    override fun doViewCreated() {
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner


        viewModel.getInitData()

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

    }

    @SuppressLint("ClickableViewAccessibility") // warning 무시용
    private fun initRecyclerView() {

        timeTableAdapter = TimeTableAdapter(viewModel)
        binding.rvTimeTable.adapter = timeTableAdapter

        val swipeHelperCallback = SwipeHelperCallback().apply {
            setClamp(resources.displayMetrics.widthPixels.toFloat() / 3)
        }
        val itemTouchHelper = ItemTouchHelper(swipeHelperCallback)
        itemTouchHelper.attachToRecyclerView(binding.rvTimeTable)

        binding.rvTimeTable.apply {
            setOnTouchListener { _, _ ->
                swipeHelperCallback.removePreviousClamp(this)
                false
            }
        }
//        timeTableAdapter.setHasStableIds(true)

    }




}