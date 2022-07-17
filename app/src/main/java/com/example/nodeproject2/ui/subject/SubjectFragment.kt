package com.example.nodeproject2.ui.subject

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.nodeproject2.R
import com.example.nodeproject2.base.BaseFragment
import com.example.nodeproject2.databinding.FragmentSubjectBinding
import com.example.nodeproject2.databinding.FragmentTimeTableBinding


class SubjectFragment : BaseFragment<FragmentSubjectBinding>(FragmentSubjectBinding::bind, R.layout.fragment_subject
) {

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