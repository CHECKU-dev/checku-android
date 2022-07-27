package com.example.nodeproject2.repository

import com.example.nodeproject2.data.remote.api.Api
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotificationRepository @Inject constructor(private val api: Api) {

    suspend fun getNotifications(
        userId: Long
    ) = api.getNotifications(userId)

    suspend fun cancelNotification(
        userId: Long,
        subjectNumber: String
    ) = api.cancelNotification(userId, subjectNumber)



}