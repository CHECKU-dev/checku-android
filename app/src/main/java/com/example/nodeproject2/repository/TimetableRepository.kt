package com.example.nodeproject2.repository

import com.example.nodeproject2.data.model.AddSubjectRequest
import com.example.nodeproject2.data.model.NotificationRequest
import com.example.nodeproject2.data.remote.api.Api
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TimetableRepository @Inject constructor(private val api: Api) {

    suspend fun getMySubjects(
        userId: Long
    ) = api.getMySubjects(userId)

    suspend fun applyNotification(
        request: NotificationRequest
    ) = api.applyNotification(request)


    suspend fun removeSubject(
        userId: Long,
        subjectNumber: String
    ) = api.removeSubject(userId, subjectNumber)


}