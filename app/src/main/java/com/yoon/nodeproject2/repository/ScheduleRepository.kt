package com.yoon.nodeproject2.repository

import com.yoon.nodeproject2.data.model.GetScheduleResponse
import com.yoon.nodeproject2.data.model.Schedule
import com.yoon.nodeproject2.data.remote.api.Api
import com.yoon.nodeproject2.di.CheckuApplication
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ScheduleRepository @Inject constructor(private val api: Api) {
    private val appDBInstance = CheckuApplication.dataBaseInstance.scheduleDao()

    suspend fun insertSchedule(scheduleList: List<Schedule>) = appDBInstance.insertSchedule(scheduleList)

    suspend fun getScheduleFromServer() = api.getSchedule()

    fun getSchedule() = appDBInstance.getSchedule()
    suspend fun updateSchedule(scheduleList:  List<Schedule>) = appDBInstance.updateSchedule(scheduleList)


}