package com.example.nodeproject2.repository

import com.example.nodeproject2.data.remote.api.LoginApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TimetableRepository @Inject constructor(private val loginApi: LoginApi) {

    suspend fun getMySubjects(
        subjects: List<String>
    ) = loginApi.getMySubjects(subjects)

}