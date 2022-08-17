package com.yoon.nodeproject2.widget.utils

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

object Utils {
    const val BASE_URL = "http://13.209.191.134:8000"

    fun getDeadline(deadline: LocalDate) : String {

        val day = ChronoUnit.DAYS.between(LocalDateTime.now().toLocalDate(), deadline)

        if (day < 0L) {
            return "마감"
        }
        return if (day == 0L) {
            "D-Day"
        }else {
            "D-$day"
        }
    }


}

object SearchRecyclerViewType {
    const val LOADING = 0
    const val CONTENT = 1
}