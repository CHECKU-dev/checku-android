package com.yoon.nodeproject2.data.model

import com.google.gson.annotations.SerializedName

data class AddOrRemoveSubjectRequest(
    @SerializedName("userId") val userId: Long,
    @SerializedName("subjectNumber") val subjectNumber: String,
)
