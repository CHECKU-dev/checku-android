package com.yoon.nodeproject2.repository

import com.yoon.nodeproject2.data.model.AddOrRemoveSubjectRequest
import com.yoon.nodeproject2.data.model.NotificationRequest
import com.yoon.nodeproject2.data.remote.api.Api
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
        request: AddOrRemoveSubjectRequest
    ) = api.addOrRemoveSubject(request)


}