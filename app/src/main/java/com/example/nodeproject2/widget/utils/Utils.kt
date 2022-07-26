package com.example.nodeproject2.widget.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

object Utils {
//    const val BASE_URL = "http://172.30.1.35:8000"

    const val BASE_URL = "http://13.209.191.134:8000"

    @RequiresApi(Build.VERSION_CODES.O)
    fun getDeadline(deadline: LocalDate) : String {
        //TODO 마감 어케할지?
        return "D-" + ChronoUnit.DAYS.between(LocalDateTime.now().toLocalDate(), deadline).toString()
    }


}