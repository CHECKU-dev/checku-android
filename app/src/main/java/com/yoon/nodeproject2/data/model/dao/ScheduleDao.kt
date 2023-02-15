package com.yoon.nodeproject2.data.model.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.yoon.nodeproject2.data.model.GetScheduleResponse
import com.yoon.nodeproject2.data.model.Schedule

@Dao
interface ScheduleDao {

    @Query("SELECT * FROM SCHEDULE")
    fun getSchedule(): List<Schedule>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertSchedule(schedule: List<Schedule>)

    @Update(onConflict = OnConflictStrategy.IGNORE)
    suspend fun  updateSchedule(schedule: List<Schedule>)
    @Query("DELETE FROM SCHEDULE")
    suspend fun deleteSchedule()

}