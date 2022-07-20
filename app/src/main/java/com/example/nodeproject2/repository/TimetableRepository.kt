package com.example.nodeproject2.repository

import com.example.nodeproject2.data.remote.api.Api
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TimetableRepository @Inject constructor(private val api: Api) {

    suspend fun getMySubjects(
        subjects: List<String>
    ) = api.getMySubjects(subjects)

}