package com.example.nodeproject2.repository

import com.example.nodeproject2.data.model.NotificationRequest
import com.example.nodeproject2.data.model.RemoveSubjectRequest
import com.example.nodeproject2.data.remote.api.Api
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TimetableRepository @Inject constructor(private val api: Api) {

    suspend fun getMySubjects(
        subjects: List<String>
    ) = api.getMySubjects(subjects)

    suspend fun applyNotification(
        request: NotificationRequest
    ) = api.applyNotification(request)

    suspend fun removeSubject(
        request: RemoveSubjectRequest
    ) = api.removeSubject(request)


}