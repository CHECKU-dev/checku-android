package com.example.nodeproject2.data.model

import com.google.gson.annotations.SerializedName

data class Notification(
    @SerializedName("subjectNumber") val subjectNumber: String,
    @SerializedName("subjectName") val subjectName: String,
    @SerializedName("professor") val professor: String,

)

