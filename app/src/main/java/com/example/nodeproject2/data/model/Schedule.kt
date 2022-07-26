package com.example.nodeproject2.data.model

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import org.jetbrains.annotations.NotNull
import java.time.LocalDateTime

@Entity(tableName = "schedule")
data class Schedule @RequiresApi(Build.VERSION_CODES.O) constructor(

    @NotNull
    @PrimaryKey
    @ColumnInfo(name = "scheduleId")
    @SerializedName("scheduleId")
    val scheduleId: Long,

    @SerializedName("title")
    val title: String,

    @SerializedName("date")
    val date: String,

    @SerializedName("time")
    val time: String,

    @NotNull
    @SerializedName("deadline")
    val deadline: LocalDateTime
)
