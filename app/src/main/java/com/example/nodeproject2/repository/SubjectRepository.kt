package com.example.nodeproject2.repository

import com.example.nodeproject2.data.model.AddSubjectRequest
import com.example.nodeproject2.data.remote.api.Api
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SubjectRepository @Inject constructor(private val api: Api) {

    suspend fun getSubjects(
        department: String,
        grade: String?,
        type: String?,
        vacancy: Boolean
    ) = api.getSubjects(department, grade!!, type!!, vacancy)

    suspend fun addSubject(
        request: AddSubjectRequest
    ) = api.addSubject(request)


}