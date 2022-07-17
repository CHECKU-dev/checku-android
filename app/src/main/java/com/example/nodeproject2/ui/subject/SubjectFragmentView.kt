package com.example.nodeproject2.ui.subject

import com.example.nodeproject2.data.model.GetSubjectsResponse

interface SubjectFragmentView {

    fun onGetSubjectsSuccess(response: GetSubjectsResponse)

    fun onGetSubjectsFailure(message: String)
}