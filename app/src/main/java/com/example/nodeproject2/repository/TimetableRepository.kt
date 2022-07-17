package com.finpo.app.repository

import com.example.nodeproject2.data.remote.api.SubjectApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TimetableRepository @Inject constructor(private val subjectApi: SubjectApi) {
    suspend fun getMySubjects(
        subjects: List<String>
    ) = subjectApi.getMySubjects(subjects)

}