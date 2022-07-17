package com.example.nodeproject2.ui.timetable

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.nodeproject2.R
import com.example.nodeproject2.base.BaseFragment
import com.example.nodeproject2.databinding.FragmentTimeTableBinding
import com.example.nodeproject2.ui.timetable.adapter.TimeTableAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TimetableFragment : BaseFragment<FragmentTimeTableBinding>(FragmentTimeTableBinding::bind, R.layout.fragment_time_table) {
    private val viewModel by viewModels<TimeTableViewModel>()
    private lateinit var timeTableAdapter: TimeTableAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_time_table, container, false)
    }

    override fun doViewCreated() {

    }


}