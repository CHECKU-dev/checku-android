package com.yoon.nodeproject2.widget.utils

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.*
import com.yoon.nodeproject2.data.model.Schedule
import com.yoon.nodeproject2.data.model.dao.ScheduleDao
import com.yoon.nodeproject2.di.CheckuApplication
import java.time.LocalDateTime

@Database(entities = arrayOf(Schedule::class), version = 2)
@TypeConverters(AppDatabase.Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun scheduleDao(): ScheduleDao

    companion object {
        val instance = Room.databaseBuilder(
            CheckuApplication.instance,
            AppDatabase::class.java,
            "schedule.db"
        )
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
    }

    class Converters {
        @RequiresApi(Build.VERSION_CODES.O)
        @TypeConverter
        fun toDate(dateString: String?): LocalDateTime? {
            return if (dateString == null) {
                null
            } else {
                LocalDateTime.parse(dateString)
            }
        }

        @TypeConverter
        fun toDateString(date: LocalDateTime?): String? {
            return date?.toString()
        }
    }

}