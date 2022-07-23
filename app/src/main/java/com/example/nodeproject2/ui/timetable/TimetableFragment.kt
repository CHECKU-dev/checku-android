package com.example.nodeproject2.ui.timetable

import android.annotation.SuppressLint
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

            println(it)
            println("================================")

            timeTableAdapter.submitList(it)
        }

    }

    @SuppressLint("ClickableViewAccessibility")
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