package com.example.nodeproject2.data.model.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.nodeproject2.data.model.Schedule

@Dao
interface ScheduleDao {

    @Query("SELECT * FROM SCHEDULE")
    fun getSchedule(): List<Schedule>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertSchedule(schedule: List<Schedule>)

}