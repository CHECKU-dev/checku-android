package com.example.nodeproject2.ui.timetable

import com.example.nodeproject2.data.model.GetSubjectsResponse

interface TimetableFragmentView {

    fun onGetSubjectsSuccess(response: GetSubjectsResponse)

    fun onGetSubjectsFailure(message: String)
}