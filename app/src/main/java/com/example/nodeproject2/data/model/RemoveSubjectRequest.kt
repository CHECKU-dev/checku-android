package com.example.nodeproject2.data.model

import com.google.gson.annotations.SerializedName

data class RemoveSubjectRequest(
    @SerializedName("userId") val userId: Long,
    @SerializedName("subjectNumber") val subjectNumber: String,
)
