package com.example.nodeproject2.data.model

import com.google.gson.annotations.SerializedName

data class AddSubjectRequest(
    @SerializedName("userId") val userId: Long,
    @SerializedName("subjectNumber") val subjectNumber: String,
)
