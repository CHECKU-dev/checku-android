package com.yoon.nodeproject2.data.model

import com.google.gson.annotations.SerializedName

data class NotificationRequest(
    @SerializedName("userId") val userId: Long,
    @SerializedName("subjectNumber") val subjectNumber: String,
    @SerializedName("subjectName") val subjectName: String,
    @SerializedName("professor") val professor: String
)
