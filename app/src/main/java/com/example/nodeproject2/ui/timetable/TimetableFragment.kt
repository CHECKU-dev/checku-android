package com.example.nodeproject2.ui.timetable

import androidx.fragment.app.viewModels
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
        }

    }

    private fun initRecyclerView() {
        timeTableAdapter = TimeTableAdapter(viewModel)
        binding.rvTimeTable.adapter = timeTableAdapter
        timeTableAdapter.setHasStableIds(true)

    }


}