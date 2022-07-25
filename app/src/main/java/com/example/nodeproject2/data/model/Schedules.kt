package com.example.nodeproject2.data.model

import com.google.gson.annotations.SerializedName
import java.time.LocalDateTime

data class Schedules(

    @SerializedName("scheduleId") val scheduleId: Long,
    @SerializedName("title") val title: String,
    @SerializedName("date") val date: String,
    @SerializedName("deadline") val deadline: LocalDateTime,

)
