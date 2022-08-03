package com.example.nodeproject2.repository

import com.example.nodeproject2.data.model.AddOrRemoveSubjectRequest
import com.example.nodeproject2.data.remote.api.Api
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SubjectRepository @Inject constructor(private val api: Api) {

    suspend fun getSubjects(
        userId : Long,
        department: String,
        grade: String?,
        type: String?,
        vacancy: Boolean
    ) = api.getSubjects(userId, department, grade!!, type!!, vacancy)

    suspend fun addOrRemoveSubject(
        request: AddOrRemoveSubjectRequest
    ) = api.addOrRemoveSubject(request)

    suspend fun getSubjectBySearch(
        searchQuery: String,
        page: Int?
    ) = api.getSubjectBySearch(searchQuery, page)


}