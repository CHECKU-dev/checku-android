package com.example.nodeproject2.repository

import com.example.nodeproject2.data.remote.api.Api
import retrofit2.http.Query
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SubjectRepository @Inject constructor(private val api: Api) {

    suspend fun getSubjects(
        department: String,
        grade: String?,
        type: String?
    ) = api.getSubjects(department, grade!!, type!!)


}