package com.yoon.nodeproject2.data.model

import com.google.gson.annotations.SerializedName

data class AddSubjectResponse(
    @SerializedName("saveSubjectId") val subjectId: Long,
)
